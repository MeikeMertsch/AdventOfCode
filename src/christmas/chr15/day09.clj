(ns christmas.chr15.day09
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(defn all-dest [input]
	(-> (str/split input #" to | = \d+\n*")
		distinct))

(defn parse-line [line]
	(->> (str/split line #" = | to ")
		 (#(vector  (into #{} (butlast %))
		 		 	(t/parse-int (last %))))))

(defn parse-file [file]
	(->> (str/split-lines file)
		 (map parse-line)
		 (apply concat)
		 (apply hash-map)))

(defn new-combo [all-destinations destinations]
	(for [x all-destinations
		  y destinations
		  :let [result (conj y x)]
		  :when (apply distinct? result)]
		result))

(defn combos [destinations]
	(->> (iterate (partial new-combo destinations) (map vector destinations))
		 (take (count destinations))
		 last))

(defn distance [distances route]
	(->> (partition 2 1 route)
		 (map (partial into #{}))
		 (map distances)
		 (apply +)))

(defn day09 [input]
	(->> (all-dest input)
		 combos
		 (map (partial distance (parse-file input)))
		 (#(vector (apply min %)(apply max %)))))