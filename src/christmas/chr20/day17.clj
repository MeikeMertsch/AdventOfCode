(ns christmas.chr20.day17
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(def neighbors [[-1 -1 -1] [-1 -1 0] [-1 -1 1] [-1 0 -1] [-1 0 1] [-1 1 -1] [-1 1 0] [-1 1 1] [-1 0 0]
                [0 -1 -1] [0 -1 0] [0 -1 1] [0 0 -1] [0 0 1] [0 1 -1] [0 1 0] [0 1 1]
                [1 -1 -1] [1 -1 0] [1 -1 1] [1 0 -1] [1 0 1] [1 1 -1] [1 1 0] [1 1 1] [1 0 0]])



(defn add-coord [coord]
  (map (partial conj coord) (range -1 2)))

(defn add-coords [all-coords]
  (->> (map add-coord all-coords)
       (apply concat)
       (set)))

(defn neighbours [dims]
  (-> (iterate add-coords [[-1] [0] [1]])
      (nth (dec dims))
      (disj (repeat dims 0))))

(def neighbors-3 (neighbours 3))
(def neighbors-4 (neighbours 4))

(defn neighbors [dims]
  ({3 neighbors-3 4 neighbors-4} dims))

(defn parse-line [n row-nr line]
  (let [func (if (= n 3) #(vector [%1 row-nr 0] (= \# %2)) #(vector [%1 row-nr 0 0] (= \# %2)))]
  (->> (map-indexed func line)
       (filter #(true? (last %)))
       (map first))))

(defn parsefile [n filename]
  (->> (slurp filename)
       (str/split-lines)
       (map-indexed (partial parse-line n))
       (apply concat)
       (set)))

(defn get-neighbors [n coords]
  (map #(map + coords %) (neighbors n)))

(defn all-neighbors [n coords]
  (->> (map (partial get-neighbors n) coords)
       (apply concat)
       (apply hash-set)
       ))

(defn count-active-neighbours [n game coords]
  (->> (get-neighbors n coords)
       (filter game)
       (count)))

(defn active? [previous count]
  (if previous
    (<= 2 count 3)
    (= 3 count)))


(defn tick-field [n game coords]
  (->> (count-active-neighbours n game coords)
       (active? (game coords))
       (vector coords)))

(defn tick [n game]
  (->> (all-neighbors n game)
       (map #(tick-field n game %))
       (filter #(true? (last %)))
       (map first)
       (set)
       ))

(defn day17 [n input]
  (->> (parsefile n input)
       (iterate (partial tick n))
       (drop 6)
       (first)
       (count)
       ))


