(ns christmas.day24
	(:require [clojure.string :as str]))

(defn parse-int [text]
	(Integer/parseInt text))

(defn parse-weaknesses [weaknesses]
	(if (nil? weaknesses) 
		[]
		(-> (str/replace weaknesses "weak to " "")
			(str/split #", ")
			(#(map keyword %)))))

(defn parse-immunities [immunities]
	(if (nil? immunities) 
		[]
		(-> (str/replace immunities "immune to " "")
			(str/split #", ")
			(#(map keyword %)))))

(defn parse-group [text]
	(let [numbers (map parse-int (re-seq #"\d+" text))
		  d-type (re-find #"\w+ damage" text)
		  weaknesses (re-find #"weak to [\w, ]+" text)
		  immunities (re-find #"immune to [\w, ]+" text)]
		{:units (first numbers) 
		 :hp (second numbers)
		 :weak (into #{} (parse-weaknesses weaknesses))
		 :immunity (into #{} (parse-immunities immunities)) 
		 :damage (nth numbers 2) 
		 :init (last numbers)
		 :d-type (keyword (first (str/split d-type #" ")))
		 :target nil}))

(defn parse-boosted-group [text]
	(let [numbers (map parse-int (re-seq #"\d+" text))
		  d-type (re-find #"\w+ damage" text)
		  weaknesses (re-find #"weak to [\w, ]+" text)
		  immunities (re-find #"immune to [\w, ]+" text)]
		{:units (first numbers) 
		 :hp (second numbers)
		 :weak (into #{} (parse-weaknesses weaknesses))
		 :immunity (into #{} (parse-immunities immunities)) 
		 :damage (+ 33 (nth numbers 2))
		 :init (last numbers)
		 :d-type (keyword (first (str/split d-type #" ")))
		 :target nil}))

(defn system [immune-system infection]
	{:immune (apply hash-set (map parse-group immune-system))
	 :viruses (apply hash-set (map parse-group infection))})

(defn boosted-system [immune-system infection]
	{:immune (apply hash-set (map parse-boosted-group immune-system))
	 :viruses (apply hash-set (map parse-group infection))})

(defn effective-power [{:keys [damage units]}]
	(* damage units))
	
(defn choosing-order [groups]
	(->> (sort-by :init > groups)
		 (sort-by effective-power >)))

(defn has-weakness [weakness group]
	(some? (weakness (group :weak))))

(defn has-immunity [immunity group]
	(some? (immunity (group :immunity))))

(defn choose-target [group rem-enemies]
	(let [weaklings (filter (partial has-weakness (:d-type group)) rem-enemies)
		  norms (remove (partial has-immunity (:d-type group)) rem-enemies)]
		(->>
			(cond
				(not-empty weaklings) weaklings
				(not-empty norms) norms
				:rest [])
			choosing-order
			first)))

(defn apply-target [[_ rem-enemies] group]
	(let [target (choose-target group rem-enemies)]
		(vector (assoc group :target (:init target))
				(remove #{target} rem-enemies))))

(defn choose-targets [friends enemies]
	(->> (choosing-order friends)
		 (reductions apply-target [[] enemies])
		 (map first)
		 rest
		 (into #{})))
	
(defn selection-phase [{:keys [immune viruses] :as system}]
	(-> (update-in system [:immune] choose-targets viruses)
		(update-in [:viruses] choose-targets immune)))

(def enemies {:immune :viruses :viruses :immune})

(defn attack-order [{:keys [immune viruses] :as system}]
	(->> (map #(vector (:init %) :immune) immune)
		 (concat (map #(vector (:init %) :viruses) viruses))
		 (sort-by first >)))

(defn find-attacker [[init combatant] system]
	(->> (combatant system)
		 (filter #(= init (:init %)))
		 first))

(defn find-target [[_ combatant] attacker system]

	(->> ((enemies combatant) system)
		 (filter #(= (:target attacker) (:init %)))
		 first))

(defn deal-damage [attacker target system [_ combatant]]
	(let [multiplyer (cond ((:weak target) (:d-type attacker)) 2
		  				   ((:immunity target) (:d-type attacker)) 0
		  				   :rest 1)
		  damage (* multiplyer (effective-power attacker))
		  destroyed-units (quot damage (:hp target))
		  upd-target (update-in target [:units] - destroyed-units)]
;		  (println damage destroyed-units)
		(-> (update-in system [(enemies combatant)] disj target)
			(#(if (< 0 (:units upd-target)) (update-in % [(enemies combatant)] conj upd-target) %)))))


(defn attack [system who]
	(let [attacker (find-attacker who system)
		  target (find-target who attacker system)]
;		  (println who)
;		  (println attacker)
;		  (println target)
		  (if (nil? target) 
		  	system
		  	(deal-damage attacker target system who))))

(defn attacking-phase [system]
	(let [order (attack-order system)]
;		(println order)
		(reduce attack system order)))

(defn tick [system]
;	(println (count (:immune system)))
;	(println (count (:viruses system)))
	(attacking-phase (selection-phase system)))



(defn day24 [immune viruses]
	(->> (system immune viruses)
		 (iterate tick)
		 (drop-while #(and (not-empty (:immune %)) (not-empty (:viruses %))))
		 first
		 (#(vector (map :units (:immune %)) (map :units (:viruses %))))
		 flatten
		 (apply +)))

(defn day24b [immune viruses]
	(->> (boosted-system immune viruses)
		 (iterate tick)
		 (drop-while #(and (not-empty (:immune %)) (not-empty (:viruses %))))
		 first
		 (#(vector (map :units (:immune %)) (map :units (:viruses %))))
		 ;flatten
		 ;(apply +)
		 ))











