(ns christmas.chr16.day05
  (:require [clojure.string :as str]
  			[christmas.tools :as t]
  			[digest :as dig]))

(defn digest [input]
	(dig/md5 input))

(defn md5 [input number]
	(digest (str input number)))

(defn build-next-entry [input number]
	(vector (inc number) (nth (md5 input number) 5)))

(defn next-digit [input [start _]]
	(println start)
	(->> (range start (+ 1000000000 start))
		 (drop-while #(not (str/starts-with? (md5 input %) "00000")))
		 first
		 (build-next-entry input)))

(defn day05 [input]
	(->> (iterate (partial next-digit input) [0 0])
		 (drop 1)
		 (take 8)
		 (map last)
		 (apply str)))

(defn update-map [m [pos digit]]
	(if (and (nil? (m pos)) (#{\0 \1 \2 \3 \4 \5 \6 \7} pos)) (assoc m pos digit) m))

(defn build-next-entry-2 [input results number]
	(->> (md5 input number)
		 (#(map (partial nth %) [5 6]))
		 (#(vector (inc number) (update-map results %)))))

(defn next-digit-2 [input [start results]]
	(println input start results)
	(->> (range start (+ 1000000000 start))
		 (drop-while #(not (str/starts-with? (md5 input %) "00000")))
		 first
		 (build-next-entry-2 input results)))

(defn day05b [input]
	(->> (iterate (partial next-digit-2 input) [1469590 {}])
		 (drop-while #(< (count (second %)) 8))
		 first
		 last
		 (sort-by first)
		 (map last)
		 (apply str)))