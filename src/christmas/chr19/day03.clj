(ns christmas.chr19.day03
  (:require [clojure.string :as str]
            [clojure.set :as s]
            [christmas.tools :as t]))

(def directions {"L" [-1 0] "R" [1 0] "U" [0 1] "D" [0 -1]})

(defn parse-instruction [[direction length]]
  (vector (directions direction) (t/parse-int length)))

(defn parse-wire [wire]
  (->> (str/split wire #",")
       (map (partial re-seq #"\d+|\w"))
       (map parse-instruction)))

(defn parse [file]
  (->> (slurp file)
       (str/split-lines)
       (map parse-wire)))

(defn walk [path [direction length]]
  (->> (peek path)
       (iterate #(mapv + direction %))
       (rest)
       (take length)
       (into path)))

(defn walk-instructions [instructions]
  (reduce walk [[0 0]] instructions))

(defn intersections [paths]
  (->> (map set paths)
       (apply s/intersection)))

(defn day03 [input]
  (->> (parse input)
       (map walk-instructions)
       (map rest)
       (intersections)
       (map t/manhatten)
       (apply min)))

(defn sketch-distances [path]
  (->> (map-indexed #(vector %2 %1) path)
       (apply concat)
       (apply hash-map)))

(defn find-fastest-intersection [distances]
  (->> (map #(dissoc % [0 0]) distances)
       (apply merge-with vector)
       (keep #(when (vector? (peek %)) (apply + (peek %))))
       (apply min)))

(defn day03b [input]
  (->> (parse input)
       (map walk-instructions)
       (map sketch-distances)
       (find-fastest-intersection)))
