(ns christmas.chr20.day06
  (:require [clojure.string :as str]
            [clojure.set :as s]))


(defn parsefile [filename]
  (->> (slurp filename)
       (#(str/split % #"\n\n"))))

(defn day06 [input]
  (->> (parsefile input)
       (map #(re-seq #"\w" %))
       (map (partial apply hash-set))
       (map count)
       (apply +)))

(defn find-equals [string]
  (->> (str/split-lines string)
       (map (partial apply hash-set))
       (apply s/intersection)
       count))

(defn day06b [input]
  (->> (parsefile input)
       (map find-equals)
       (apply +)))


