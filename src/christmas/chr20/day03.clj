(ns christmas.chr20.day03
  (:require [clojure.string :as str]
            [christmas.tools :as t]))


(defn parse [filename]
  (->> (slurp filename)
       (str/split-lines)))

(defn positions [length width]
  (->> (range)
       rest
       (take length)
       (map (partial * 3))
       (map #(mod % width))))

(defn hit [field]
  (->> (positions (count field) (count (first field)))
       (map #(nth %1 %2) field)))

(defn day03 [input]
  (->> (parse input)
       rest
       hit
       (filter #{\#})
       count))


(defn positions2 [length width right]
  (->> (range)
       (take (inc length))
       (map (partial * right))
       (map #(mod % width))))

(defn hit2 [field [right down]]
  (->> (positions2 (count field) (count (first field)) right)
       (map #(nth %1 %2) (take-nth down field))
       ;
       ))

(defn day03b [filename inputs]
  (->> inputs
       (map #(hit2 (parse filename) %))
       (map #(filter #{\#} %))
       (map count)
       (apply *)
       ))


