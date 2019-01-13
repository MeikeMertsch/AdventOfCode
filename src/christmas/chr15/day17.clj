(ns christmas.chr15.day17
  (:require [clojure.string :as str]))

(defn new-combo [all-destinations destinations]
	(for [x all-destinations
		  y destinations
		  :let [result (conj y x)]
		  :when (apply distinct? result)
		  :when (apply <= (map second result))]
		result))

(defn maximum [destinations sum]
	(->> (map last destinations)
		 (sort)
		 (reductions +)
		 (take-while (partial >= sum))
		 count))

(defn minimum [destinations sum]
	(->> (map last destinations)
		 (sort)
		 (reductions +)
		 (drop-while (partial >= sum))
		 count))

(defn day17 [destinations sum]
	(->> (iterate (partial new-combo destinations) (map vector destinations))
		 (take (maximum destinations sum))
		 (drop (- (maximum destinations sum) (minimum destinations sum)))
		 (apply concat)
		 (filter #(= sum (apply + (map last %))))
		 (map (partial sort-by first))
		 distinct
		 (map (partial map last))
		 count))

(defn cant-contain-eggnog? [sum destinations]
	(->> (map (partial map last) destinations)
		 (map (partial apply +))
		 (not-any? (partial = sum))))

(defn day17b [destinations sum]
	(->> (iterate (partial new-combo destinations) (map vector destinations))
		 (drop-while (partial cant-contain-eggnog? sum))
		 first
		 (filter #(= sum (apply + (map last %))))
		 (map (partial sort-by first))
		 distinct
		 (map (partial map last))
		 count
		 ))

