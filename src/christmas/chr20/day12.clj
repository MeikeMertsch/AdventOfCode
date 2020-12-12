(ns christmas.chr20.day12
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn parse-line [[dir num]]
  [(keyword (str dir)) (t/parse-int num)])

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (map #(re-seq #"\d+|\w" %))
       (map parse-line)))

(defn forward [{:keys [dir north east]} amount]
  (->> {:E [0 1]   :W [0 -1]   :N [1 0]   :S [-1 0]}
       (dir)
       (map (partial * amount))
       (map + [north east])
       (#(hash-map :dir dir :north (first %) :east (last %)))))

(defn east [game amount]
  (update game :east (partial + amount)))

(defn west [game amount]
  (east game (- amount)))

(defn north [game amount]
  (update game :north (partial + amount)))

(defn south [game amount]
  (north game (- amount)))

(defn turn [orientations game amount]
  (->> (cycle orientations)
       (drop-while #(not= % (:dir game)))
       (#(nth % (/ amount 90)))
       (#(hash-map :dir % :north (:north game) :east (:east game)))))

(defn right [game amount]
  (turn [:N :E :S :W] game amount))

(defn left [game amount]
  (turn [:N :W :S :E] game amount))

(defn tick [game [direction amount]]
  ;(println game direction amount)

  (-> (direction {:F forward :E east :W west :N north :S south :R right :L left})
      (#(% game amount))))

(defn day12 [input]
  (->> (parsefile input)
       (reduce tick (hash-map :dir :E :north 0 :east 0))
       (#(t/manhatten [(:east %) (:north %)]))))


(defn wp-f [{:keys [dir north east wp-n wp-e]}  amount]
  (->> [wp-n wp-e]
       (map (partial * amount))
       (map + [north east])
       (#(hash-map :dir dir :north (first %) :east (last %) :wp-n wp-n :wp-e wp-e))))

(defn wp-e [game amount]
  (update game :wp-e (partial + amount)))

(defn wp-w [game amount]
  (wp-e game (- amount)))

(defn wp-n [game amount]
  (update game :wp-n (partial + amount)))

(defn wp-s [game amount]
  (wp-n game (- amount)))

(defn wp-turn [orientations game amount]
  (->> (cycle orientations)
       (drop-while #(not= % (:dir game)))
       (#(nth % (/ amount 90)))
       (#(hash-map :dir % :north (:north game) :east (:east game)))))

(defn turn-wp-r [[wp-n wp-e]]
  [(- wp-e) wp-n])

(defn turn-wp-l [[wp-n wp-e]]
  [wp-e (- wp-n)])

(defn wp-turn [game amount pattern]
  (->> (iterate pattern [(:wp-n game) (:wp-e game)])
       (drop (/ amount 90))
       (first)
       (#(hash-map :dir (:dir game) :north (:north game) :east (:east game) :wp-n (first %) :wp-e (last %)))))

(defn wp-r [game amount]
  (wp-turn game amount turn-wp-r))

(defn wp-l [game amount]
  (wp-turn game amount turn-wp-l))
  
(defn wp-tick [game [direction amount]]
  (-> (direction {:F wp-f :E wp-e :W wp-w :N wp-n :S wp-s :R wp-r :L wp-l})
      (#(% game amount))))

(defn day12b [input]
  (->> (parsefile input)
       (reduce wp-tick (hash-map :dir :E :north 0 :east 0 :wp-n 1 :wp-e 10))
       (#(t/manhatten [(:east %) (:north %)]))
       ))


