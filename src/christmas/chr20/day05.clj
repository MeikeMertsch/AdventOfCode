(ns christmas.chr20.day05
  (:require [clojure.string :as str]
            [christmas.tools :as t]))


(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (map #(replace {\B 1 \F 0 \L 0 \R 1} %))
       (map #(split-at 7 %))))

(def powers-of-two (iterate (partial * 2) 1))

(defn transform [number]
  (->> (reverse number)
       (map * powers-of-two)
       (apply +)))

(defn coordinates [[row column]]
  [(transform row) (transform column)])

(defn day05 [input]
  (->> (parsefile input)
       (map coordinates)
       (map #(+ (last %) (* 8 (first %))))
       sort
       ;last
       ))


