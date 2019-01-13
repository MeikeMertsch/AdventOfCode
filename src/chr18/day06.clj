(ns christmas.day06
  (:require [christmas.core :refer :all]
            [clojure.string :as str]))

(defn cab [[x1 x2][y1 y2]]
	(+ (Math/abs (- x1 y1))
	   (Math/abs (- x2 y2))))

(defn board [file]
	[[(apply min (map first file)) (apply min (map last file))]
	 [(apply max (map first file)) (apply max (map last file))]])

(defn whoisclosest [coords file]
	(let [closest (map #(cab coords %) file)]
	(->> (zipmap file closest)
		 (filter #(= (last %) (apply min closest)))
		 (#(if (< 1 (count %)) 
		 	         ""
		 	         (first (last %))))
		 )))

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
	(->> (mapv #(split-at % my_list) (range 1 (count my_list)))
		 (mapv #(concat (first %) (rest (last %))))))

(defn fnb [total ind1 ind2]
	(mapv #(vector ind1 ind2 %) (range (inc ind2) total)))

(defn fna [total ind1]
	(mapcat #(fnb total ind1 %) (range (inc ind1) (dec total))))

(defn findallperms [my_list]
	(->> (mapcat #(fna (count my_list) %) (range (dec (dec (count my_list)))))
		 (mapv #(vector  (nth my_list (first %))
		 				(nth my_list (second %))
		 				(nth my_list (last %))))))


(defn inmiddle [x file]
	(->> (remove #{x} file)
		 findallperms
		 (map #(coordsinmiddle x %))
		 (some true?)))

(defn whoIsInMiddle [file]
	(filter #(inmiddle % file) file))

(defn allboardcoords [board]
	(let [xcoords (range (ffirst board) (inc (first (last board))))
		  ycoords (range (last (first board)) (inc (last (last board))))]
         (->> (mapcat (fn [ycoord] (map #(vector % ycoord) xcoords)) ycoords)
;         	  (into #{})
         	  )))

(defn allnearest [allboardcoords file]
	(map #(whoisclosest % file) allboardcoords))

(defn countcoordsonboard [coords closestlist]
	(->> (filter #(= coords %) closestlist)
		 count))
	
(defn countPerCoords [setOfCoords countedBoard]
	(->> (map #(countcoordsonboard % countedBoard) setOfCoords)
		(apply max)))

(defn exercise06 [file]
	(countPerCoords (whoIsInMiddle file)
		 (allnearest (allboardcoords (board file)) file)))

(defn calcalldistances [coords file] 
	(apply + (map #(cab coords %) file)))

(defn exercise06b [file maxC]
	(->> (map #(calcalldistances % file) (allboardcoords (board file)))
    (filter #(< % maxC))
    count))