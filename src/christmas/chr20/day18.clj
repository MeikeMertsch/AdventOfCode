(ns christmas.chr20.day18
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))


(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (map #(re-seq #"\(|\)|\d+|\+|\*" %))))

(def signs {"+" + "*" *})

(defn eva [[a x b]]
  [((signs x) a b)])

(defn check [pieces]
  (if (= 3 (count (last pieces)))
    (assoc pieces (dec (count pieces)) (eva (last pieces)))
    pieces))

(defn number [result input]
  (->> (t/parse-int (str input))
       (t/add-to-last result)
       (check)))

(defn plus [result _]
  (t/add-to-last result "+"))

(defn times [result _]
  (t/add-to-last result "*"))

(defn open [result _]
  (conj result []))

(defn close [result _]
  (-> (subvec result 0 (dec (count result)))
      (number (t/llast result))))

(def actions {"+" plus "(" open ")" close "*" times})

(defn tick [result input]
  (->> (actions input number)
       (#(% result input))))

(defn day18 [input]
  (->> (parsefile input)
       (map #(reduce tick [[]] %))
       (map t/llast)
       (apply +)))


