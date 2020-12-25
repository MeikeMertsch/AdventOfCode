(ns christmas.chr20.day19
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn parse-piece [piece]
  (->> (str/split piece #" ")))

(defn possibilities [text]
  (->> (str/split text #"( \| )")
       (map parse-piece)))

(defn parse-rule [rule]
  (->> (str/split rule #": ")
       (#(vector (first %) (possibilities (last %))))))

(defn id-ed [rules]
  (->> (:identified rules)
       (keys)
       (set)))

(defn find-next [rules]
  (->> (:rules rules)
       (drop-while #(not-every? (id-ed rules) (flatten (last %))))
       (first)))

(defn pos [identified result key]

  (->> (map #(map (partial apply str %) (identified key)) result)
       (apply concat)))

(defn decipher [identified value]
  (->> (reduce (partial pos identified) [""] value)))

(defn possies [values identified]
  (->> (map (partial decipher identified) values)
       (apply concat)))

(defn tick-with [[key value] rules]
  (-> (update rules :rules #(dissoc % key))
      (update :identified #(assoc % key (possies value (:identified rules))))))

(defn tick-rule [rules]
  (-> (find-next rules)
      (tick-with rules)))

(defn find-a-b [rules]
  (->> (filter #(or (= [["a"]] (last %))
                    (= [["b"]] (last %))) rules)
       (apply concat)
       (apply hash-map)))

(defn tick-rules [rules]
  (->> (find-a-b rules)
       (hash-map :identified)
       (#(assoc % :rules (apply dissoc rules (keys (:identified %)))))
       (iterate tick-rule)
       (drop-while #(nil? ((:identified %) "0")))
       (first)
       (:identified)
       (#(% "0"))
       ))

(defn parse-parts [[rules messages]]
  {:rules (->> (str/split-lines rules)
               (map parse-rule)
               (apply concat)
               (apply hash-map)
               (tick-rules)
               (set))
   :messages (str/split-lines messages)})

(defn parsefile [filename]
  (-> (slurp filename)
      (str/replace #"\"" "")
      (str/split #"\n\n")
      (parse-parts)))

(defn validate [rules message]
  (rules message))

(defn day19 [input]
  (->> (parsefile input)
       (#(filter (partial validate (:rules %)) (:messages %)))
       (count)))

(defn day19b [input]
  (->> (parsefile input)
       (:rules)
       
       ;(#(filter (partial validate (:rules %)) (:messages %)))
       (count)
       ))
