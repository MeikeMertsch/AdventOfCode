(ns christmas.chr15.day20
  (:require [clojure.string :as str]))

(defn sum-of-divisors [number]
	(->> (range 1 (Math/floor (inc (Math/sqrt number))))
		 (filter #(zero? (rem number %)))
		 (map #(+ % (if-let [x (= % (/ number %))] 0 (/ number %))))
		 (apply +)))

(defn day20 [input]
	(->> (range)
		 (map sum-of-divisors)
		 (take-while #(< % (/ input 10)))
		 count))

(defn sum-of-divisors-filtered [number]
	(->> (range 1 (inc 50))
		 (map #(vector (/ number %) (rem number %)))
		 (filter #(= 0 (second %)))
		 (map first)
		 (apply +)))

(defn day20c [input]
	(->> (range)
		 (map sum-of-divisors-filtered)
		 (take-while #(< % (/ input 11)))
		 count))