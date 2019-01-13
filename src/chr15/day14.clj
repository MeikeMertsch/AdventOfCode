(ns christmas.chr15.day14
  (:require [clojure.string :as str]))

(defn reindeer [length [speed burst pause]]
	(->> (repeat 0)
		 (take pause)
		 (concat (take burst (repeat speed)))
		 cycle
		 (take length)
		 (reductions +)))

(defn day14 [input length]
	(->> (map (partial reindeer length) input)
		 (map last)
		 (apply max)))

(defn give-points [reindeers]
	(let [maximum (apply max reindeers)]
		(map #(if (= % maximum) 1 0) reindeers)))

(defn day14b [input length]
	(->> (map (partial reindeer length) input)
		 (apply map vector)
		 (map give-points)
		 (apply map +)
		 (apply max)))