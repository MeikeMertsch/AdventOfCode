(ns christmas.chr16.day17
  (:require [clojure.string :as str]
  			       [digest :as dig]))

(def open-door? #{\b \c \d \e \f})

(defn in-vault? [path]
  (let [counts (frequencies path)]
  (and (= (- (counts \R 0) (counts \L 0)) 3)
       (= (- (counts \D 0) (counts \U 0)) 3))))

(defn in-dungeon? [path]
  (let [counts (frequencies path)]
  (and (<= 0 (- (counts \R 0) (counts \L 0)) 3)
       (<= 0 (- (counts \D 0) (counts \U 0)) 3))))

(defn walk[input way]
  (->> (dig/md5 (str input way))
       (map vector "UDLR")
       (filter #(open-door? (last %)))
       (map first)
       (map (partial str way))
       (filter in-dungeon?)))

(defn tick [input candidates]
  (->> (map (partial walk input) candidates)
  					(apply concat)))

(defn program [input]
  (->> (iterate (partial tick input) [""])
       (drop-while #(and (not-empty %) 
                         (not-any? in-vault? %)))
       first
       (filter in-vault?)
       first))

(defn tick2 [input {:keys [candidates longest-way]}]
  (->> (map (partial walk input) candidates)
  					(apply concat)
  					(#(hash-map :candidates (remove in-vault? %) 
  					            :longest-way (if (some in-vault? %) 
  					                             (count (first %))
  					                             longest-way)))))

(defn program2 [input]
  (->> (iterate (partial tick2 input) {:candidates [""] :longest-way 0})
       (drop 1)
       (drop-while #(not-empty (:candidates %)))
       first
       :longest-way))
      

