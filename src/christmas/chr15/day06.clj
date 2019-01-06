(ns christmas.chr15.day06
  (:require [clojure.string :as str]))

(defn create-grid [size]
	(-> (for [x (range size)
			  y (range size)]
			[x y])
		(zipmap (repeat false))))

(def toggle (partial reduce #(update %1 %2 not)))
(def on (partial reduce #(update %1 %2 (constantly true))))
(def off (partial reduce #(update %1 %2 (constantly false))))

(defn turn [f grid [x0 y0 x1 y1]]
	(->> (for [ x (range x0 (inc x1))
				y (range y0 (inc y1))]
		  	[x y])
		 (f grid)))

(defn execute [grid [instruction places]]
	(cond
		(#{:toggle} instruction) (turn toggle grid places)
		(#{:on} instruction) (turn on grid places)
		(#{:off} instruction) (turn off grid places)))

(defn day06 [input]
	(->> (reduce execute (create-grid 1000) input)
		 vals
		 (filter true?)
		 count))

(defn create-grid-2 [size]
	(-> (for [x (range size)
			  y (range size)]
			[x y])
		(zipmap (repeat 0))))

(def toggle-2 (partial reduce #(update %1 %2 (partial + 2))))
(def on-2 (partial reduce #(update %1 %2 inc)))
(def off-2 (partial reduce #(update %1 %2 (fn [value] (max (dec value) 0)))))

(defn execute-2 [grid [instruction places]]
	(cond
		(#{:toggle} instruction) (turn toggle-2 grid places)
		(#{:on} instruction) (turn on-2 grid places)
		(#{:off} instruction) (turn off-2 grid places)))

(defn day06b [input]
	(->> (reduce execute-2 (create-grid-2 1000) input)
		 vals
		 (apply +)))