(ns christmas.chr20.day02
  (:require [clojure.string :as str]
            [christmas.tools :as t]))

(defn turn [[min max which string]]
  (vector (t/parse-int min) (t/parse-int max) which string))

(defn parse [filename]
  (->> (slurp filename)
       (str/split-lines)
       (map #(re-seq #"[\w|\d]+" %))
       (map turn)))

(defn filtered [[min max which string]]
  (vector min max which (count (filter #(= (first which) %) string))))

(defn safe-nth [pos string]
  (if (< (dec pos) (count string))
    (nth string (dec pos)) "xx"))

(defn prep [[eins zwei which string]]
  [(hash-set (safe-nth eins string) (safe-nth zwei string)) (first which)])

(defn day02 [input]
  (->> (parse input)
       (map filtered)
       (filter #(<= (first %) (last %) (second %)))
       count))

(defn day02b [input]
  (->> (parse input)
       (map prep)
       (filter #(and (contains? (first %) (last %)) 
                     (= 2 (count (first %)))
                     ))
       count
       ))

