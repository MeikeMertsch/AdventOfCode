(ns christmas.chr18.day03
  (:require [christmas.core :refer :all]
            [clojure.string :as str]))

(def file03
	(->> (slurp "resources/chr18/Input03real")
		 (clojure.string/split-lines)))


(defn getCoordinates [my_set]
	
     (->> (map #(str/split % #" ") my_set)
     	(map #(drop 2 %))
     	(map #(vector (str/split (first %) #",") (str/split (last %) #"x") ))
     	flatten
     	(map #(str/replace % #":" ""))
     	(map bigint)
     	(map int)
     	(partition 4)
	))

(defn getAllSquares [my_set]
	(for [x (range (first my_set) (+ (first my_set) (nth my_set 2))) 
          y (range (nth my_set 1) (+ (nth my_set 1) (nth my_set 3)))]
  [x y]))
	

(defn exercise03 []
	(->> (getCoordinates file03)
		(mapcat getAllSquares)
		sort
		(partition-by identity)
		(remove #(= 1 (count %)))
		count
		
	))

(defn xrange [my_set]
	(into #{} (range (first my_set) (+ (first my_set) (nth my_set 2)))))

(defn yrange [my_set]
	(into #{} (range (nth my_set 1) (+ (nth my_set 1) (nth my_set 3)))))

(defn allSquareCoords []
	(->> (getCoordinates file03)
		 (map #(vector (xrange %)(yrange %)))
		
		;(map getAllSquares)
		; (map vector  
		 ; #(range (first %) (+ (first %) (nth % 2))))
		 ; #(range (nth % 1) (+ (nth % 1) (nth % 3))))
		    ))
		  
(defn overlapsCoord [first_set second_set]
;	(println first_set)
;	(println second_set)
	(and (some #(contains? first_set %) second_set)
		 (some #(contains? second_set %) first_set)))	 
		
(defn overlaps [first_set second_set]
	(and (overlapsCoord (first first_set) (first second_set))
		 (overlapsCoord (last first_set) (last second_set))))
	
(defn cleanedSet [all_coords my_coords_set]
	(remove #(= my_coords_set %) all_coords))


(defn overlapsWithAny [all_coords my_coords_set]
	(some #(overlaps my_coords_set %) (cleanedSet all_coords my_coords_set)))

(defn duplicateSquares []
	(->> (getCoordinates file03)
		(map getAllSquares)
		(apply concat)
		sort
		(partition-by identity)
		(remove #(= 1 (count %)))
		(map first)
		(into #{})
		
	))



(defn hasDuplicateSquares [my_set]
	(some #(contains? (duplicateSquares) %) my_set))

(defn exercise03b []

	(->> (allSquareCoords)
		 
		 (remove #(overlapsWithAny (allSquareCoords) %))
		 (map max)
		 
	))