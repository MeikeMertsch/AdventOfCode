(ns christmas.chr20.day19
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn parse-piece [piece]
  (->> (str/split piece #" ")))

(defn possibilities-old [text]
  (->> (str/split text #"( \| )")
       (map parse-piece)))

(defn possibilities [text]
  (re-seq #"a|b|\||\d+" text))


(defn parse-rule [rule]
  (->> (str/split rule #": ")
       (#(vector (first %) (possibilities (last %))))))

(defn parse-parts [[rules messages]]
  {:rules (->> (str/split-lines rules)
               (mapcat parse-rule)
               (apply hash-map))
   :messages (str/split-lines messages)})

(defn parsefile [filename]
  (-> (slurp filename)
      (str/replace #"\"" "")
      (str/split #"\n\n")
      (parse-parts)))

(defn prep-a-b [[key [value]]]
  [key value])

(defn find-a-b [rules]
  (->> (filter #(#{["a"] ["b"]} (last %)) rules)
       (mapcat prep-a-b)
       (concat ["|" "|"])
       (apply hash-map)))

(defn identified? [id-ed [_ value]]
  (every? #((set (keys id-ed)) %) value))

(defn rule [[key values] id-ed]
  (->> (map id-ed values)
       (apply str)
       (#(str "(" % ")"))
       (vector key)))

(defn save-id-ed [[key value] rules]
  (-> (update rules :instructions dissoc key)
      (update :id-ed assoc key value)))

(defn build [{:keys [instructions id-ed] :as rules}]
  (-> (t/find-first (partial identified? id-ed) instructions)
      (rule id-ed)
      (save-id-ed rules)))

(defn build-rules [rules]
  (->> (update rules :instructions #(apply dissoc % (keys (:id-ed rules))))
       (iterate build)
       (t/find-first #(empty? (:instructions %)))
       (:id-ed)))

(defn init [rules]
  (->> (find-a-b rules)
       (hash-map :instructions rules :id-ed)
       (build-rules)))

(defn match-all [pattern messages]
  (map (partial re-matches pattern) messages))

(defn validate [{:keys [rules messages] :as game}]
  (-> (rules "0")
      (re-pattern)
      (match-all messages)
      (#(filter some? %))))

(defn day19 [input]
  (-> (parsefile input)
      (update :rules init)
      (validate)
      (count)))

(defn build-11 [remaining-rules repeats]
  (apply str (concat (repeat repeats (remaining-rules "42"))
                     (repeat repeats (remaining-rules "31")))))

(defn find-11 [remaining-rules]
  (->> (map (partial build-11 remaining-rules) (range 1 5))
       (str/join "|")
       (#(str "(" % ")"))
       (vector "11")))

(defn find-8 [remaining-rules]
  (-> (rule ["8" ["42"]] remaining-rules)
      (update 1 str "+")))

(defn expanded-rule-0 [rules]
  (->> (select-keys rules ["42" "31"])
       (#(map (fn [search-fn] (search-fn %)) [find-8 find-11]))
       (map last)
       (apply str)))

(defn validate-recursive [{:keys [rules messages]}]
  (-> (expanded-rule-0 rules)
      (re-pattern)
      (match-all messages)
      (#(map first %))
      (#(filter some? %))))

(defn day19b [input]
  (-> (parsefile input)
      (update :rules init)
      (validate-recursive)
      (count)))


