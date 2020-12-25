(ns christmas.chr20.day25
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]
            [clojure.pprint :as pp]))

(defn parse [input]
  (->> (slurp input)
       (str/split-lines)
       (map t/parse-int)))

(defn transform [subj number]
  (-> (* subj number)
       (rem 20201227)))

(defn move[[a b]]
  (->> (iterate #(transform 7 %) 1)
       (take-while #(not (#{a b} %)))
       (#(vector (count %) ({a b b a} (transform 7 (last %)))))))

(defn day25 [input]
  (->> (parse input)
       (move)
       (#(take (inc (first %)) (iterate (partial transform (last %)) 1)))
       (last)
       ))

