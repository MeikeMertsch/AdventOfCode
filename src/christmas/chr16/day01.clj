(ns christmas.chr16.day01
  (:require [clojure.string :as str]
  			[christmas.tools :as t]))

(def turn {\N {\L \W \R \E}
		   \W {\L \S \R \N}
		   \S {\L \E \R \W}
		   \E {\L \N \R \S}})

(defn move [[compass _][direction length]]
	[((turn compass) direction)
	 length])

(defn get-all [direction directions]
	(->> (filter #(= direction (first %)) directions)
		 (map last)
		 (apply +)))

(defn x [directions]
	(Math/abs 
	(- (get-all \E directions)
	   (get-all \W directions))))

(defn y [directions]
	(Math/abs 
	(- (get-all \S directions)
	   (get-all \N directions))))

(defn translate [input]
	(->> (str/split input #", ")
		 (map #(vector (first %) (t/parse-int (apply str (rest %)))))
		 (reductions move [\N 0])))

(defn day01 [input]
	(->> (translate input)
		 (#(vector (x %) (y %)))
		 (apply +)))

(def directions {\S [0 1] \W [-1 0] \E [1 0] \N [0 -1]})

(defn walk [places [direction length]]
	(->> (take length (repeat (directions direction)))
		 (reductions (partial map +) (last places))
		 (drop 1)
		 (concat places)))

(defn step [[positions last-pos] pos]
	[(conj positions last-pos) pos])

(defn distance [pos]
	(->> (map #(Math/abs %) pos)
		 (apply +)))

(defn day01b [input]
	(->> (translate input)
		 (drop 1)
		 (reduce walk [[0 0]])
		 (drop 1)
		 (reductions step [#{} [0 0]])
		 (drop-while (fn [[m p]] (nil? (m p))))
		 first 
		 last
		 distance
		 ;(map-indexed (fn [idx positions] (if ())))
		 ;(drop-while #(false? ()))
		 ))