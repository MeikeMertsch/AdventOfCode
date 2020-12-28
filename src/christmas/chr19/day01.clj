(ns christmas.chr19.day01
  (:require [clojure.string :as str]
            [christmas.tools :as t]))

(defn parse [file]
  (->> (slurp file)
       (str/split-lines)
       (map t/parse-int)))

(defn fuel [mass]
  (-> (/ mass 3)
      (int)
      (- 2)))

(defn day01 [input]
  (->> (parse input)
       (map fuel)
       (apply +)))

(defn full-fuel [mass]
  (->> (iterate fuel mass)
       (take-while (partial < 0))
       (rest)
       (apply +)))

(defn day01b [input]
  (->> (parse input)
       (map full-fuel)
       (apply +)))
