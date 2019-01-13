(ns christmas.chr15.day03
  (:require [clojure.string :as str]))

(def dir {\^ [0 -1] \v [0 1] \> [1 0] \< [-1 0]})

(defn walk [pos direction]
	(->> (dir direction)
		 (map + pos)))

(defn houses [input]
	(distinct (reductions walk [0 0] input)))

(defn day03 [input]
	(count (houses input)))

(defn day03b [input]
	(->> (partition 2 input)
		 (#(concat (houses (map first %))
		 	  	   (houses (map last %))))
		 distinct
		 count))