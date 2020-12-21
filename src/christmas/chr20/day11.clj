(ns christmas.chr20.day11
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn generate-entry [row-number thing column-number]
  ({\L [row-number column-number] \. nil} thing))

(defn generate-line [line row-number]
  (map #(generate-entry row-number %1 %2) line (range (count line))))

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (#(map (fn [a b] (generate-line b a)) (range (count %)) %))
       (apply concat)
       (filter some?)
       (#(zipmap % (repeat false)))))

(def steps [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]])

(defn neighbor [coords]
  (map (partial map + coords) steps))

(defn tick-space [game coords]
  (->> (neighbor coords)
       (map game)
       (filter true?)
       (count)
       (#(if (= 0 %)
           (vector coords true)
           (if (< % 4)
             (find game coords)
             (vector coords false)
             )))))

(defn tick [game]
  (->> (map (partial tick-space game) (keys game))
       (apply concat)
       (apply hash-map)))

(defn day11 [input]
  (->> (parsefile input)
       (iterate tick)
       (map #(count (filter true? (vals %))))
       (partition 2 1)
       (drop-while #(not= (first %) (last %)))
       (ffirst)
       ))

(defn visible[game coords]
  )