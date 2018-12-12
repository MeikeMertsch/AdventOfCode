(ns christmas.day06b
  (:require [christmas.core :refer :all]
            [clojure.string :as str]))

(defn iinc [n]
	(inc (inc n)))

(defn genCoords [[x y] gen]
	(let [generation (range (inc gen))]
		(concat
		(map #(vector (+ x %)(+ y (- gen %))) generation)
		(map #(vector (- x %)(- y (- gen %))) generation)
		(map #(vector (+ x %)(- y (- gen %))) (range 1 gen))
		(map #(vector (- x %)(+ y (- gen %))) (range 1 gen)))))
	
(defn size [coords]
	(->>
	(map #(* (first (nth coords %)) 
		     (- (last (nth coords (inc %) (nth coords 0))) 
		     	(last (nth coords (dec %) (nth coords (dec (count coords)))))))
		(range (count coords)))
	(apply +)
	(#(if (neg? %) (* -1 %) %))))

(defn coordsinmiddle [x [a b c]]
	(neg?
		(- (max (size [a b c x])
	     	    (size [a b x c])
		        (size [a x b c]))
	       (size [a b c]))))

(defn reduceList [my_list]
	(->> (map #(split-at % my_list) (range 1 (count my_list)))
		 (map #(concat (first %) (rest (last %))))))

(defn fnb [total ind1 ind2]
	(map #(vector ind1 ind2 %) (range (inc ind2) total)))

(defn fna [total ind1]
	(mapcat #(fnb total ind1 %) (range (inc ind1) (dec total))))

(defn findallperms [my_list]
	(->> (mapcat #(fna (count my_list) %) (range (dec (dec (count my_list)))))
		 (map #(vector  (nth my_list (first %))
		 				(nth my_list (second %))
		 				(nth my_list (last %))))))


(defn inmiddle [x file]
	(->> (remove #{x} file)
		 findallperms
		 (map #(coordsinmiddle x %))
		 (some true?)))

(defn whoIsInMiddle [file]
	(filter #(inmiddle % file) file))