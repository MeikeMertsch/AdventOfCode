(ns christmas.chr15.day15
  (:require [clojure.string :as str]))

(defn munch-ingredients [ingredients amounts]
	(->> (map #(map (partial * %1) %2) amounts ingredients)
		 (apply map +)))

(defn total-score [ingredients amounts]
	(->> (munch-ingredients ingredients amounts)
		 butlast
		 (map (partial max 0))
		 (apply *)))

(defn calories [ingredients amounts]
	(last (munch-ingredients ingredients amounts)))

(defn combos [input]
	(for [a (range (inc 100))
		  b (range (inc 100))
		  c (range (inc 100))
		  d (range (inc 100))
		  :when (= 100 (+ a b c d))
		  :let [score (total-score input [a b c d])]
		  :when (pos? score)]
		score))

(defn calorie-combos [input]
	(for [a (range (inc 100))
		  b (range (inc 100))
		  c (range (inc 100))
		  d (range (inc 100))
		  :when (= 100 (+ a b c d))
		  :let [score (total-score input [a b c d])]
		  :when (pos? score)
		  :when (= 500 (calories input [a b c d]))]
		score))

(defn day15 [input]
	(apply max (combos input)))
	
(defn day15b [input]
	(apply max (calorie-combos input)))
	
