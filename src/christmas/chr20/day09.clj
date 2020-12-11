(ns christmas.chr20.day09
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (map bigint)))

(defn validate [numbers]
  (->> (drop-last numbers)
       (map #(- (last numbers) %))
       (into #{})
       (s/intersection (into #{} (drop-last numbers)))
       seq))

(defn day09 [input preamble]
  (->> (parsefile input)
       (partition (inc preamble) 1)
       (drop-while validate)
       (first)
       (last)))

(defn find-contiguous-set [preset input droppers]
  (->> (drop droppers input)
       (reductions +)
       (take-while #(<= % preset))
       (#(if (= preset (last %))
           (sort (take (count %) (drop droppers input)))
           nil))))

(defn day09b [input preset]
  (->> (parsefile input)
       (take-while (partial not= preset))
       (#(map (partial find-contiguous-set preset %) (range)))
       (drop-while nil?)
       (first)
       (#(+ (first %) (last %)))))
