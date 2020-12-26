(ns christmas.chr20.day23
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]
            [clojure.pprint :as pp]))

(defn parse [input cups]
  (->> (first input)
       (conj input)
       (partition 2 1)
       (apply concat)
       (apply hash-map)
       (hash-map :cups cups :current (first input) :ring)))

(defn find-triplet [{:keys [current ring] :as game}]
  (->> (iterate ring current)
       (rest)
       (take 3)
       (assoc game :triplet)))

(defn wrapped-dec [cups number]
  (let [decr (dec number)]
    (if (= 0 decr) cups decr)))

(defn find-destination [{:keys [current triplet cups] :as game}]
  (->> (iterate #(wrapped-dec cups %) current)
       (rest)
       (t/find-first #(not ((set triplet) %)))
       (assoc game :destination)))

(defn change-ring [{:keys [current ring triplet destination] :as game}]
  (-> (update game :ring assoc destination (first triplet))
      (update :ring assoc current (ring (last triplet) (last triplet)))
      (update :ring assoc (last triplet) (ring destination destination))))

(defn find-next [{:keys [current ring] :as game}]
  (assoc game :current (ring current)))

(defn play [game]
  (-> (find-triplet game)
      (find-destination)
      (change-ring)
      (find-next)))

(defn output [game]
  (->> (:ring game)
       (#(iterate % 1))
       (rest)
       (take 8)
       (apply str)
       (t/parse-int)))

(defn day23 [input moves]
  (->> (parse input 9)
       (iterate play)
       (#(nth % moves))
       (output)))

(defn init [cups input]
  (-> (iterate #(conj % (inc (count %))) input)
      (nth  (- cups (count input)))
      (conj (first input))
      (#(partition 2 1 %))
      (#(apply concat %))
      (#(apply hash-map %))))


(defn map-init [cups input]
  (->> (init cups input)
       (hash-map :cups cups :current (first input) :ring)))

(defn day23b [input moves cups]
  (->> (map-init cups input)
       (iterate play)
       (take moves)
       last
       :ring
       (#(vector (% 1) (% (% 1))))
       (apply *)))