(ns christmas.chr20.day14
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)))

(defn bump-number [num [pos act]]
  (if (= \0 act)
    (bit-clear num pos)
    (bit-set num pos)))

(defn update-num [mapping instruction]
  (->> (re-seq #"\d+" instruction)
       (map t/parse-int)
       (#(update mapping :mems assoc (first %) (reduce bump-number (last %) (:mask mapping))))))

(defn prep-mask [instruction]
  (->> (re-find #"[X|0|1]+" instruction)
       (map vector (range 35 -1 -1))))

(defn tick [mapping instruction]
  (->> (if (str/starts-with? instruction "mask")
         (assoc mapping :mask (remove #(= \X (last %)) (prep-mask instruction)))
         (update-num mapping instruction))))

(defn day14 [input]
  (->> (parsefile input)
       (reduce tick {:mems {}})
       (:mems)
       (map last)
       (apply +)))

(defn both [num pos]
  (vector (bit-clear num pos)
          (bit-set num pos)))

(defn zero [num pos]
  (vector num))

(defn one [num pos]
  (vector (bit-set num pos)))

(def actions {\0 zero
              \1 one
              \X both})

(defn bump-addresses [num [pos act]]
  ((actions act) num pos))

(defn apply-mask [address-list mask-bit]
  (mapcat #(bump-addresses % mask-bit) address-list))

(defn update-addresses [{:keys [mask] :as mapping} [address value]]
  (->> (reduce apply-mask [address] mask)
       (interpose value)
       (vec)
       (#(conj % value))
       (#(update mapping :mems (fn [mems] (apply assoc mems %))))))

(defn decode [mapping instruction]
  (->> (re-seq #"\d+" instruction)
       (map t/parse-int)
       (update-addresses mapping)))


(defn do-decoding [mapping instruction]
  (->> (if (str/starts-with? instruction "mask")
         (assoc mapping :mask (prep-mask instruction))
         (decode mapping instruction))))

(defn day14b [input]
  (->> (parsefile input)
       (reduce do-decoding {:mems {}})
       (:mems)
       (map last)
       (apply +)))





