(ns christmas.chr19.day02
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [christmas.chr19.intcode :as i]))

(defn day02 [input noun verb]
  (->> (i/parse input)
       (i/set-noun-verb noun verb)
       (i/run-program)))

(defn apply-sentence [input noun]
  (map #(i/set-noun-verb noun % input) (range 100)))

(defn run [input]
  (vector (i/run-program input)
          (vals (select-keys (:program input) [1 2]))))

(defn output[[_ [noun verb]]]
  (+ (* 100 noun) verb))

(defn gravity-assist [all-runs]
  (->> (t/find-first #(= 19690720 (first %)) all-runs)
       (output)))

(defn day02b [input]
  (->> (i/parse input)
       (#(mapcat (partial apply-sentence %) (range 100)))
       (map run)
       (gravity-assist)))
