(ns christmas.chr15.day11
  (:require [clojure.string :as str]))

(def regex-consecutive #".*(abc|bcd|cde|def|efg|fgh|ghi|hij|ijk|jkl|klm|lmn|mno|nop|opq|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz).*")
(def regex-forbidden #"[^i^o^l]{8}")
(def regex-pairs #".*([a-z])\1.*(^\1)\2.*")

(defn re [regex string]
;	(println (re-find regex string) string)
	(re-find regex string))

(defn not-valid? [candidate]
	(nil? 
		(and (re regex-consecutive candidate)
		 (re regex-forbidden candidate)
		 (re regex-pairs candidate)
		 )))


(defn next-char [single]
	({\a \b \b \c \c \d \d \e \e \f \f \g \g \h \h \j \i \j \j \k \k \m \l \m \m \n \n \p \o \p \p \q \q \r \r \s \s \t \t \u \u \v \v \w \w \x \x \y \y \z } single))



(def multiplicators (->> (range 7 -1 -1)
		 (map #(repeat % 26))
		 (map (partial apply *))))

(defn to-number [transformed]
	(->> (map * multiplicators transformed)
		 (apply +)))

(defn to-seq [number]
	(->> (reductions 
			(fn [[n _] m] (let [digit (quot n m)] (vector (- n (* m digit))
			digit)))
			[number 0] multiplicators)
		 rest
		 (map second)))

(defn transform [string]
	(->> (seq string)
		 (map int)
		 (map (partial + -97))))

(defn back-transform [sequence]
	(->> (map (partial + 97) sequence)
		 (map char)
		 (apply str)))

(defn increase [string]
	(->> (transform string)
		 to-number
		 inc
		 to-seq
		 back-transform))

(defn no-forbidden? [sequence]
	(nil? (some #{8 11 14} sequence)))

(defn consecutive? [sequence]
	(->> (partition 3 1 sequence)
		 (some (fn [[a b c]] (= (inc a) b (dec c))))))

(defn pairs? [sequence]
	(->> (partition-by identity sequence)
		 (remove #(= 1 (count %)))
		 (map first)
		 distinct
		 count
		 (< 1)))

(defn day11 [string]
	(->> (transform string)
		 to-number
		 (#(range % 500000000000))
;		 (take 5)
		 (map to-seq)
		 (map #(vector 	(back-transform %) 
		 				(consecutive? %)
		 				(no-forbidden? %)
		 				(pairs? %)))
		 (map #(vector (first %) (every? true? (rest %))))
		 (drop-while #(false? (last %)))
		 ffirst

		 ))
