(ns christmas.chr20.day15
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))


(defn parse [filename]
  (->> (slurp filename)
       (re-seq #"\d+")
       (map t/parse-int)))

(defn tick [previous]
  (->> (rest previous)
       (take-while #(not= (first previous) %))
       (count)
       (inc)
       (#(if (= % (count previous)) 0 %))
       (conj previous)))

(defn day15 [input]
  (->> (parse input)
       (reverse)
       (iterate tick)
       (#(nth % (- 2020 (count (parse input)))))
       (first)))

(defn initialize [input]
  (->> (range)
       (rest)
       (map vector (butlast input))
       (apply concat)
       (apply hash-map)
       (hash-map :last (last input) :round (count input) :last-occurence)))

(defn next-number [{:keys [last last-occurence round] :as numbers}]
  (->> (last-occurence last round)
       (- round)))

(defn speak [numbers]
  (-> (assoc numbers :last (next-number numbers))
      (update :last-occurence assoc (:last numbers) (:round numbers))
      (update :round inc)))

(defn day15b [input]
  (->> (parse input)
       (initialize)
       (iterate speak)
       (#(nth % (- 30000000 (count (parse input)))))
       (:last)))

