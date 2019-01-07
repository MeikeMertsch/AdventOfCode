(ns christmas.chr15.day10
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(defn conf [piece]
	[(count piece) (first piece)])

(defn tick [number]
	(->> (partition-by identity number)
		 (mapcat conf)
		 (apply str)))

(defn day10 [number times]
	(->> (iterate tick (str number))
		 (drop times)
		 first))
