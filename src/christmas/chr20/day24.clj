(ns christmas.chr20.day24
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]
            [clojure.pprint :as pp]))

(def dirs {"e" [2 0] "se" [1 -2] "sw" [-1 -2] "w" [-2 0] "nw" [-1 2] "ne" [1 2]})

(defn parse-line [line]
  (->> (re-seq #"e|se|sw|w|nw|ne" line)
       (map dirs)
       (apply map +)))

(defn parse [input]
  (->> (slurp input)
       (str/split-lines)
       (map parse-line)))

(defn black-tiles [tiles]
  (->> (frequencies tiles)
       (filter #(odd? (last %)))
       (map first)
       (set)))

(defn day24 [input]
  (->> (parse input)
       (black-tiles)
       (count)))

(def hex-pattern [[2 0] [-2 0] [1 2] [1 -2] [-1 2] [-1 -2]])

(defn hex-neighbors [coords]
  (map #(map + coords %) hex-pattern))

(defn all-flippable [tiles]
  (->> (mapcat hex-neighbors tiles)
       (concat tiles)
       (distinct)))

(defn black? [black-before? black-neighbors]
  (if black-before?
    (#{1 2} black-neighbors)
    (= 2 black-neighbors)))

(defn flip [black-tiles tile]
  (->> (hex-neighbors tile)
       (filter black-tiles)
       (count)
       (black? (black-tiles tile))))

(defn redecorate [black-tiles]
  (->> (all-flippable black-tiles)
       (filter (partial flip black-tiles))
       (set)))

(defn day24b [input]
  (->> (parse input)
       (black-tiles)
       (iterate redecorate)
       (#(nth % 100))
       (count)))

