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
       (t/find-first #(nil? ((set triplet) %)))
       (assoc game :destination)))

(defn change-ring [{:keys [current ring triplet destination] :as game}]
  (-> (update game :ring assoc destination (first triplet))
      (update :ring assoc current (ring (last triplet)))
      (update :ring assoc (last triplet) (ring destination))))

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

(defn mark [game cup]
  (if (= cup (:current game)) (str cup) cup))

(defn output-b [game]
  (->> (:ring game)
       (#(iterate % 1))
       (take (:cups game))
       (map #(mark game %))
       ))

(defn day23 [input moves]
  (->> (parse input 9)
       (iterate play)
       (#(nth % moves));(inc moves)))
       (output)))

(defn day23b [input moves cups]
  (->> (parse input cups)
       (iterate play)
       (take 100);(inc moves)))
       (map output-b)))

