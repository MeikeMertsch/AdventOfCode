(ns christmas.chr15.day22
  (:require [clojure.string :as str]))

(defn decrease [mana amount]
	(-> (update mana :amount - amount)
		(update :steps conj amount)))

(defn magic-missile [game]
	(-> (update-in game [:player :mana] decrease 53)
		(update-in [:boss :hp] - 4)))

(defn drain [game]
	(-> (update-in game [:player :mana] decrease 73)
		(update-in [:boss :hp] - 2)
		(update-in [:player :hp] + 2)))

(defn shield [game]
	(-> (update-in game [:player :mana] decrease 113)
		(update :effects assoc :shield 6)))

(defn poison [game]
	(-> (update-in game [:player :mana] decrease 173)
		(update :effects assoc :poison 6)))

(defn recharge [game]
	(-> (update-in game [:player :mana] decrease 229)
		(update :effects assoc :recharge 5)))

(defn apply-shield [game]
	(update-in game [:effects :shield] dec))

(defn apply-poison [game]
	(-> (update-in game [:effects :poison] dec)
		(update-in [:boss :hp] - 3)))

(defn apply-recharge [game]
	(-> (update-in game [:effects :recharge] dec)
		(update-in [:player :mana :amount] + 101)))

(def actions {:magic-missile magic-missile :drain drain :shield shield :poison poison :recharge recharge})
(def actions-cost {53 :magic-missile 73 :drain 113 :shield 173 :poison 229 :recharge})
(def effects {:shield apply-shield :poison apply-poison :recharge apply-recharge})

(defn apply-effects [game]
	(->> (:effects game)
		 (remove (fn [[n c]] (zero? c)))
		 (map first)
		 (reduce #((effects %2) %1) game)))

(defn boss-move [game]
	(let [shielded (< 0 (get-in game[:effects :shield] 0))
		  boss-damage (get-in game [:boss :damage])
		  netto-damage (if shielded (- boss-damage 7) boss-damage)]
		(update-in game [:player :hp] - netto-damage)))

(defn too-costly-actions [game]
	(let [mana-to-spend (get-in game [:player :mana :amount] 0)]
		(map last (filter #(< mana-to-spend (first %)) actions-cost))))

(defn running-effects [game]
	(map first (filter #(pos? (last %)) (:effects game))))

(defn boss-turn [games]
	(map boss-move games))

(defn remove-losses [games]
	(remove #(< (get-in % [:player :hp] 0) 1) games))

(defn all-actions [game]
	(->> (apply dissoc actions (running-effects game))
		 (#(apply dissoc % (too-costly-actions game)))
		 vals
		 (map #(% game))))

(defn player-turn [games]
	(mapcat all-actions games))

(defn save-wins [status]
	(let [grouped (group-by #(< (get-in % [:boss :hp]) 1) (:games status))
		  won (grouped true)
		  open (grouped false)]
		(-> (assoc status :wins won)
			(assoc :games (remove-losses open)))))

(defn take-turn [{:keys [games p-turn] :as status}]
	(let [after-effects (update status :games (partial map apply-effects))
		  assigned-turn (update after-effects :p-turn not)]
		(-> (if p-turn
				(update assigned-turn :games player-turn)
				(update assigned-turn :games boss-turn))
			save-wins)))

(defn day22 [game]
	(->> (iterate take-turn {:games [game] :p-turn true})
		 (drop-while #(nil? (:wins %)))
		 (take-while #(not-empty (:games %)))
		 (mapcat :wins)
		 (map (comp :steps :mana :player))
		 (map (partial apply +))
		 (apply min)))

(defn remove-one-point [game]
	(update-in game [:player :hp] dec))

(defn all-actions-hard [game]
	(->> (apply dissoc actions (running-effects game))
		 (#(apply dissoc % (too-costly-actions game)))
		 vals
		 (map #(% (remove-one-point game)))))

(defn player-turn-hard [games]
	(mapcat all-actions-hard games))

(defn take-turn-hard [{:keys [games p-turn] :as status}]
	(let [after-effects (update status :games (partial map apply-effects))
		  assigned-turn (update after-effects :p-turn not)]
		(-> (if p-turn
				(update assigned-turn :games player-turn-hard)
				(update assigned-turn :games boss-turn))
			save-wins)))

(defn day22b [game]
	(->> (iterate take-turn-hard {:games [game] :p-turn true})
		 (drop-while #(nil? (:wins %)))
		 (take-while #(not-empty (:games %)))
		 (mapcat :wins)
		 (map (comp :steps :mana :player))
		 (map (partial apply +))
		 (apply min)))


























