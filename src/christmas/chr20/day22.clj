(ns christmas.chr20.day22
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]
            [clojure.pprint :as pp]))

(defn parse-line [line]
  (->> (str/split line #"contains")
       (map #(re-seq #"\w+" %))))

(defn parse [input]
  (->> (slurp input)
       (str/split-lines)
       (map parse-line)))

(defn process-allergene [ingredients mapping allergene]
  (if (mapping allergene)
    (update mapping allergene conj ingredients)
    (assoc mapping allergene [ingredients])))

(defn process-ingredients [mapping [ingredients allergenes]]
  (reduce #(process-allergene ingredients %1 %2) mapping allergenes))

(defn by-allergene [lines]
  (reduce process-ingredients {} lines))

(defn suspects [allergenes]
  (t/map-kv #(apply s/intersection (map set %)) allergenes))

(defn register-found [mapping [allergene ingredients]]
  (-> (update mapping :found assoc allergene (first ingredients))
      (update :suspects dissoc allergene)
      (update :suspects #(t/map-kv (fn [x] (disj x (first ingredients))) %))))

(defn solve [mapping]
  (->> (:suspects mapping)
       (filter #(= 1 (count (last %))))
       (reduce register-found mapping)))

(defn find-combo [suspects]
  (->> (hash-map :suspects suspects :found {})
       (iterate solve)
       (drop-while #(not-empty (:suspects %)))
       (first)
       (:found)))

(defn decipher-allergenes [lines]
  (->> (by-allergene lines)
       (suspects)
       (find-combo)))

(defn all-ingredients [lines]
  (->> (map first lines)
       (apply concat)))

(defn day22 [input]
  (->> (parse input)
       ((fn [lines] (remove #((set (vals (decipher-allergenes lines))) %) (all-ingredients lines))))
       (count)))

(defn day22b [input]
  (->> (parse input)
       (decipher-allergenes)
       (sort-by first)
       (map last)
       (str/join ",")))


