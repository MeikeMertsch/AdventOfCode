(ns christmas.chr20.day13
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn parse-line [[timestamp busses]]
  {:busses (->> (re-seq #"\d+" busses) (map t/parse-int))
   :timestamp (t/parse-int timestamp)})

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (parse-line)))

(defn waiting [timestamp bus-nr]
  (->> (/ timestamp bus-nr)
       (#(Math/ceil %))
       (int)
       (* bus-nr)
       (- timestamp)
       (#(vector bus-nr (- %)))))

(defn day13 [input]
  (->> (parsefile input)
       (#(map (partial waiting (:timestamp %)) (:busses %)))
       (sort-by last)
       (first)
       (apply *)))

(defn parse-bus [idx bus]
  (if (= "x" bus)
    nil
    [idx (bigint (t/parse-int bus))]))

(defn parse [filename]
  (->> (slurp filename)
       (str/split-lines)
       (last)
       (#(str/split % #","))
       (map-indexed parse-bus)
       (filter some?)
       (sort-by last >)
       (#(conj (sort-by first (rest %)) (first %))))) ; <---- just because that is what I used in my example

(defn init [count-offset [[offset bus-nr] & others]]
  (->> (quot count-offset bus-nr)
       (* bus-nr)
       (#(- % offset))
       (hash-map :busses others :multiplier bus-nr :start)))

(defn new-start [start multiplier [offset bus-nr]]
  (->> (iterate (partial + multiplier) start)
       (t/find-first #(= 0 (mod (+ % offset) bus-nr)))))

(defn use-bus [{:keys [busses multiplier start] :as mapping}]
  (-> (update mapping :busses rest)
      (update :multiplier * (t/lfirst busses))
      (update :start new-start multiplier (first busses))))

(defn day13b [input offset]
  (->> (parse input)
       (init offset)
       (iterate use-bus)
       (t/find-first #(empty? (:busses %)))
       (:start)))

