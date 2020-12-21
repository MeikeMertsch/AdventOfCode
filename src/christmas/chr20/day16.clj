(ns christmas.chr20.day16
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))

(defn parse-own [line]
  (->> (re-seq #"\d+" line)
       (map t/parse-int)))

(defn parse-instr-line [line]
  (->> (parse-own line)
       (partition 2)))

(defn parse-instr [lines]
  (->> (str/split-lines lines)
       (map parse-instr-line)))

(defn parse-tickets [lines]
  (->> (str/split-lines lines)
       (map parse-own)))

(defn parsefile [filename]
  (->> (slurp filename)
       (#(str/split % #"\nyour ticket:|\nnearby tickets:\n"))
       (map #(%1 %2) [parse-instr parse-own parse-tickets])))

(defn fits? [[a b] number]
  ;(println a b number)
  (<= a number b))

(defn fits-instr? [[x y] number]
  ;(println x y number)
  (or (fits? x number)
      (fits? y number)))

;(defn find-valids [[instructions _ tickets]]
;  (->> (apply concat tickets)
 ;      (vector (apply concat instructions))
  ;     ;(filter #(not-any? (fn [instr] (fits? instr %)) (apply concat instructions)))
   ;    ))

(defn valid-1? [instructions ticket]
  (filter #(not-any? (fn [instr] (fits? instr %)) (apply concat instructions)) ticket))

(defn valid-2? [instructions ticket]
  (not-any? #(not-any? (fn [instr] (fits? instr %)) (apply concat instructions)) ticket))

(defn find-valids [[instructions own tickets]]
  [instructions (vec own) (filter #(valid-2? instructions %) tickets)])

(defn find-invalids [[instructions own tickets]]
  (->> (map #(valid-1? instructions %) tickets)
       (flatten)))

(defn day16 [input]
  (->> (parsefile input)
       (find-invalids)
       (apply +)))



(defn check-instr [instruction ticket-column]
  (every? #(fits-instr? instruction %) ticket-column))

(defn check-instruction [instruction ticket-columns]
  ;(println instruction ticket-columns)
  (->> (map #(check-instr instruction %) ticket-columns)
       (map vector (range))
       (filter #(true? (last %)))
       (map first)))

(defn another-thing [[instructions own ticket-columns]]
  [own (map #(check-instruction % ticket-columns) instructions)])


(defn move [result [pos possibilities]]
  (->> (vals result)
       (apply disj possibilities)
       (#(assoc result pos (first %)))))

(defn find-departures [[own possibles]]
  (->> (map set possibles)
       (map vector (range))
       (sort-by #(count (last %)))
       (reduce move {})
       (#(map % (range 6)))
       (map (partial get own))
       ;(#(map))
       )
  )

(defn day16b [input]
  (->> (parsefile input)
       (find-valids)
       (#(update % 2 (partial apply map vector)))
       (another-thing)
       (find-departures)
       (apply *)
       ))



