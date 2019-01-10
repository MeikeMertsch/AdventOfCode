(ns christmas.chr15.day21
	(:require [clojure.string :as str]
			  [christmas.tools :as t]))

(defn create-weapon [[_ cost damage]]
	{:cost cost :damage damage})

(defn create-armor [[_ cost armor]]
	{:cost cost :armor armor})

(defn create-ring [[_ cost damage armor]]
	{:cost cost :armor armor :damage damage})

(def weapons (map create-weapon 
	[["Dagger" 8 4]
	 ["Shortsword" 10 5]
	 ["Warhammer" 25 6]
	 ["Longsword" 40 7]
	 ["Greataxe" 74 8]]))

(def armors (map create-armor 
	[["nothing" 0 0]
	 ["Leather" 13 1]
	 ["Chainmail" 31 2]
	 ["Splintmail" 53 3]
	 ["Bandedmail" 75 4]
	 ["Platemail" 102 5]]))

(def rings (map create-ring 
	[["nothing" 0 0 0]
	 ["Damage +1" 25 1 0]
	 ["Damage +2" 50 2 0]
	 ["Damage +3" 100 3 0]
	 ["Defense +1" 20 0 1]
	 ["Defense +2" 40 0 2]
	 ["Defense +3" 80 0 3]]))

(defn parse-boss [input]
	(->> (re-seq #"\d+" input)
		 (map t/parse-int)
		 (zipmap [:hp :damage :armor])))

(defn combos []
	(let [no-ring (create-ring ["nothing" 0 0 0])]
	(for   [w weapons
			a armors
			r1 rings
			r2 rings
			:when (or (not= r1 r2)
					   (= r1 r2 no-ring))]
			[w a r1 r2])))

(defn equip [player equipment]
	(apply merge-with + (cons player equipment)))

(defn rounds-to-win [{:keys [damage]} {:keys [hp armor]}]
	(->> (- damage armor)
		 (max 1)
		 (/ hp)
		 (Math/ceil)
		 int))

(defn wins? [player boss]
	(< (rounds-to-win player boss)
	   (inc (rounds-to-win boss player))))

(defn day21 [player boss]
	(->> (combos)
		 (map (partial equip player))
		 (filter #(wins? % boss))
		 (map :cost)
		 (apply min)))

(defn day21b [player boss]
	(->> (combos)
		 (map (partial equip player))
		 (remove #(wins? % boss))
		 (map :cost)
		 (apply max)))