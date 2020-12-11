(ns christmas.chr20.day07
  (:require [clojure.string :as str]
            [christmas.tools :as t]))

(defn amounts [some-list]
  (->> (second some-list)
       (re-seq #"\d|\w+ \w+")
       (reverse)
       (#(if (= ["no other"] %)
           {}
           (->> (partition 2 %)
                (map (fn [[a b]] (vector a (t/parse-int b))))
                (flatten)
                (apply hash-map))))))

(defn parse-line [instruction]
  (->> (str/split instruction #" contain ")
       (#(vector (re-find #"\d|\w+ \w+" (first %)) (amounts %)))))

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (map parse-line)
       (apply concat)
       (apply hash-map)))

(defn find-bag [bagged bags]
  (->> (filter #(contains? (set (keys (second %))) bagged) bags)
       (map first)))

(defn find-new-actives [bag-field]
  (->> (map #(find-bag % (:bags bag-field)) (:active bag-field))
       (apply concat)
       (remove (:found bag-field))))

(defn tick [bag-field]
  (let [new-actives (find-new-actives bag-field)]
    (-> (assoc-in bag-field [:active] new-actives)
        (update-in [:found] #(apply conj % new-actives)))))

(defn day07 [input]
  (->> (parsefile input)
       (hash-map :found #{} :active ["shiny gold"] :bags)
       (iterate tick)
       (drop-while #(seq (:active %)))
       (first)
       (:found)
       (count)))

(defn find-contained-bags [[bag amount] bag-field]
  (->> (:bags bag-field)
       (#(% bag))
       (t/map-kv (partial * amount))
       (into [])))

(defn tick2 [bag-field]
  (let [new-actives (apply concat (map #(find-contained-bags % bag-field) (:active bag-field)))]
    (-> (assoc-in bag-field [:active] new-actives)
        (update-in [:found] #(concat % new-actives)))))

(defn day07b [input]
  (->> (parsefile input)
       (hash-map :found [] :active [["shiny gold" 1]] :bags)
       (iterate tick2)
       (drop-while #(seq (:active %)))
       (first)
       (:found)
       (map second)
       (apply +)))

