(ns christmas.chr15.day25
  (:require [clojure.string :as str]))

(defn pos-row-1 [column]
	(->> (range)
		 (reductions +)
		 (drop 1)
		 (take column)
		 last))

(defn position [column row]
	(->> (range)
		 (drop column)
		 (reductions + (pos-row-1 column))
		 (take row)
		 last))

(defn function [number]
	(rem (* number 252533) 33554393))

(defn day25 [column row]
	(->> (iterate function 20151125) 
		 (take (position column row)) 
		 last))
