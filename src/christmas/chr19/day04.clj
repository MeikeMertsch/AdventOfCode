(ns christmas.chr19.day04
  (:require [clojure.string :as str]
            [christmas.tools :as tt]))

(def basic-pred #(<= 2 %))
(def strict-pred #{2})

(defn parse [file]
  (->> (slurp file)
       (re-seq #"\d+")
       (map tt/parse-int)))

(defn password? [pred number]
  (->> (str number)
       (map #(tt/parse-int (str %)))
       (#(and
          (apply <= %)
          (->> (partition-by identity %) 
               (map count) 
               (some pred))))))

(defn count-valids [pred numbers]
  (->> (apply range numbers)
       (filter (partial password? pred))
       (count)))

(defn day04 [input]
  (->> (parse input)
       (count-valids basic-pred)))

(defn day04b [input]
  (->> (parse input)
       (count-valids strict-pred)))