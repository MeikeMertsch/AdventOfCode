(ns christmas.chr20.day13
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn parse-line [[timestamp busses]]
  {:busses (->> (re-seq #"\d+" busses) (map t/parse-int))
   :timestamp (t/parse-int timestamp)})

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (parse-line)))

(defn waiting [timestamp bus-nr]
  (->> (/ timestamp bus-nr)
       (#(Math/ceil %))
       (int)
       (* bus-nr)
       (- timestamp)
       (#(vector bus-nr (- %)))))

(defn day13 [input]
  (->> (parsefile input)
       (#(map (partial waiting (:timestamp %)) (:busses %)))
       (sort-by last)
       (first)
       (apply *)))

(defn parse [filename]
  (->> (slurp filename)
       (str/split-lines)
       (rest)
       (first)
       (#(str/split % #","))
       (map #(vector %2 %1) (range))
       (remove #(= "x" (first %)))
       (map #(update % 0 t/parse-int))
       (sort-by first >)))

(defn validate[[[biggest offset] &others]]
  (->> (map #(+ offset (* biggest %)) (range))
  (take 1000)
  ))

(defn day13b [input]
  (->> (parse input)
       (validate)
       ))


