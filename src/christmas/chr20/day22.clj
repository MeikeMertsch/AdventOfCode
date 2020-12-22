(ns christmas.chr20.day22
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]
            [clojure.pprint :as pp]))

(defn parse [input]
  (->> (str/split (slurp input) #"\n\n")
       (map str/split-lines)
       (map rest)
       (mapv (partial mapv t/parse-int))))

(defn comparison[game]
  (->> (apply map vector game)
       (first)))

(defn winner[[a b]]
  (if (< a b) 1 0))

(defn play[game]
  (->> (comparison game)
       (#(update game (winner %) conj (apply max %) (apply min %)))
       (t/map-kv rest)
       (mapv vec)))

(defn day22 [input]
  (->> (parse input)
       (iterate play)
       (t/find-first #(some empty? %))
       (t/find-first not-empty)
       (reverse)
       (map * (rest (range)))
       (apply +)))

