(ns christmas.chr15.day18
  (:require [clojure.string :as str]))

(def status {\# true \. false})

(defn parse-file [input]
	(->> (str/split-lines input)
		 (map-indexed (fn [idx itm] (map-indexed #(vector [%1 idx] (status %2)) itm)))
		 (apply concat)
		 (apply concat)
		 (apply hash-map)))

(defn neighbours [[x0 y0]]
	(for [x [(dec x0) x0 (inc x0)]
		  y [(dec y0) y0 (inc y0)]
		  :when (not (and (= x0 x) (= y0 y)))]
		[x y]))

(defn on? [status position]
	(let [glowing-neighbours (count (filter true? (map #(status % false) (neighbours position))))]
		(if (status position)
			(some? (#{2 3} glowing-neighbours))
			(= 3 glowing-neighbours)))) 

(defn tick [status]
	(->> (keys status)
		 (sort-by (juxt first last))
		 (map #(vector % (on? status %)))
		 (apply concat)
		 (apply hash-map)))

(defn day18 [input]
	(->> (parse-file input)
		 (iterate tick)
		 (drop 100)
		 first
		 vals
		 (filter true?)
		 count))

(defn on-2? [status position]
	(let [glowing-neighbours (count (filter true? (map #(status % false) (neighbours position))))]
		(if (#{[0 0][0 99][99 0][99 99]} position) true
		(if (status position)
			(some? (#{2 3} glowing-neighbours))
			(= 3 glowing-neighbours))))) 

(defn tick-2 [status]
	(->> (keys status)
		 (sort-by (juxt first last))
		 (map #(vector % (on-2? status %)))
		 (apply concat)
		 (apply hash-map)))

(defn day18b [input]
	(->> (parse-file input)
		 (iterate tick-2)
		 (drop 100)
		 first
		 vals
		 (filter true?)
		 count))
