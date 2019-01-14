(ns christmas.chr16.day08
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(defn rect [a b _ _ grid]
	(->> (for [x (range a)
			   y (range b)]
			[x y])
		 (reduce (fn [g coords] (update g coords (constantly true))) grid)))

(defn temp [b [x y] rows grid]
	(grid [x (rem (+ rows (- y b)) rows)]))

(defn rotate-column [a b _ r grid]
	(->> (for [x [a]
			   y (range r)]
			[x y])
		 (reduce (fn [g coords] (update g coords (constantly (temp b coords r grid)))) grid)))

(defn temp-2 [b [x y] columns grid]
	(grid [(rem (+ columns (- x b)) columns) y]))

(defn rotate-row [a b c _ grid]
	(->> (for [x (range c)
			   y [a]]
			[x y])
		 (reduce (fn [g coords] (update g coords (constantly (temp-2 b coords c grid)))) grid)))

(def instructions {"rect" rect "rotate c" rotate-column "rotate r" rotate-row})

(defn parse-pieces [[instr a b]]
	[(instructions instr)
	 (t/parse-int a)
	 (t/parse-int b)])

(defn parse-line [line]
	(->> (re-seq #"rect|rotate c|rotate r|\d+" line)
		 parse-pieces))

(defn initiate-grid [columns rows]
	(-> (for [x (range columns)
		  	  y (range rows)]
			[x y])
		 (zipmap (repeat false))))

(defn parse-file [input]
	(->> (str/split-lines input)
		 (map parse-line)))

(defn apply-instruction [c r grid [instr a b]]
	(instr a b c r grid))

(defn day08 [input columns rows]
	(->> (parse-file input)
		 (reduce (partial apply-instruction columns rows) (initiate-grid columns rows))
		 vals
		 (filter true?)
		 count))

(defn create-line [columns grid row]
	(println (map {true "#" false " "} (map #(grid [% row]) (range columns)))))

(defn day08b [input columns rows]
	(let [grid (->> (parse-file input) (reduce (partial apply-instruction columns rows) (initiate-grid columns rows)))]
		(map (partial create-line columns grid) (range rows))))
