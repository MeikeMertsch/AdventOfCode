(ns christmas.day20b
	(:require [clojure.string :as str]))

(def dir {\S [0 1] \W [-1 0] \E [1 0] \N [0 -1]})

(defn walk-dir [direction start]
	(map + (dir direction) start))

(defn parse-direction [[coords all-distances] candidate]
	(let [new-pos (walk-dir candidate coords)
		  old-dist (all-distances coords)
		  new-distances (assoc all-distances new-pos (min (get all-distances new-pos 50000) (inc old-dist)))]
	[new-pos new-distances]))

(defn parse-char [[coords all-distances :as last-result] candidate]
	(parse-direction last-result candidate))

(defn day20 [text]
	(->> (reductions #(parse-direction %1 %2) [[0 0] {[0 0] 0}] (rest text))
		 (take 4)))
;___________________________________________



(defn step [origin direction]
	(map + (dir direction) origin))

(defn determine-distances [all-distances [pos distance]]
	(assoc  all-distances 
			pos 
			(min distance 
			  	 (all-distances pos 10000000))))

(defn walk-directions [origin-distance origin directions]
	(->> (reductions step origin directions)
		 (#(map vector % (drop origin-distance (range))))
		 (reduce determine-distances {})))



(defn group-input [input]
	(->> (partition-by #{\( \) \|})
		 (map (partial apply str))))

