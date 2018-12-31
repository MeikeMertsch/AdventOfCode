(ns christmas.day23
	(:require [clojure.string :as str]))

(defn find-strongest [positions]
	(->> (sort-by last > positions)
		 first))

(defn distance [pos bot]
	(->> (map - pos bot)
		 (map #(Math/abs %))
		 (apply +)))

(defn all-distances [pos positions]
	(map (partial distance pos) positions))
	


(defn day23 [input]
 	(let [strongest (find-strongest input)]
		(->> (first strongest)
		 	 (#(all-distances % (map first input)))
		 	 (filter (partial >= (last strongest)))
		 	 count)))

;------------------------------------------

(defn c-range [[c-min c-max] coord]
	(range (- c-min coord) (inc (- c-max coord))))

(defn cube [[pos1 r1] [pos2 r2]]
	(map vector
		(map #(max (- %1 r1) (- %2 r2)) pos1 pos2)
		(map #(min (+ %1 r1) (+ %2 r2)) pos1 pos2)))

(defn single-coords [eff-r y-range x-coord z-coord]
	(->> (map #(if (= eff-r (Math/abs %)) %) y-range)
		 (remove nil?)
		 (map #(vector x-coord % z-coord))))

(defn plane-coords [eff-r x-range y-range z-coord]
	(mapcat #(single-coords (- eff-r (Math/abs %)) y-range % z-coord) x-range))
	
(defn convert-coords [real coordinates]
	(map #(map + real %) coordinates))	

(defn shell-coords [[x y z] r cube]
	(->> (mapcat #(plane-coords (- r (Math/abs %)) 
								(c-range (first cube) x) 
								(c-range (second cube) y)
								%) 
				(c-range (last cube) z))
		(convert-coords [x y z])))

(defn single-overlap [[[x1 y1 z1 :as pos1] r1] [[x2 y2 z2 :as pos2] r2]]
;	(println "xxx" pos1 r1 pos2 r2)
	(let [d (distance pos1 pos2)
		  rw (+ r1 r2)
		  cube (cube [pos1 r1][pos2 r2])]
		(->> (map (into #{} (shell-coords pos1 r1 cube))
		  		  (shell-coords pos2 r2 cube))
			 (remove nil?))))

(defn radius-pairs [dist r1 r2]
;	(println dist r1 r2)
	(->> (map #(if (<= 0 (- dist %) r1) 
		 		[(- dist %) %]) 
	     		(range (inc r2)))
		 (remove nil?)))

(defn all-overlaps [[pos1 r1] [pos2 r2]]
	(->> (radius-pairs (distance pos1 pos2) r1 r2)
		 (mapcat #(single-overlap [pos1 (first %)][pos2 (last %)]))))

(defn get-all-overlaps [[overlapping rem-positions]]
;	(println (count rem-positions))
	(time (->> (mapcat (partial all-overlaps (first rem-positions)) (rest rem-positions))
		 (concat overlapping)
		 (#(vector % (rest rem-positions))))))

(defn day23b [input]
	(->> (iterate get-all-overlaps [[] input])
		 (drop-while #(< 0 (count (last %))))
		 ffirst
		 frequencies
		 (sort-by second >)
		 (partition-by second)
		 ffirst
		 ;(sort-by (comp count last) >)
		 ;(sort-by)
		)
	)


;---------------------------------------

(defn overlaps? [[pos1 r1] [pos2 r2]]
	(<= (distance pos1 pos2) (+ r1 r2)))

(defn overlaps [pos positions]
	(->> (map (partial overlaps? pos) positions)
		 (filter true?)
		 count))

(defn diff [[pos1 r1] [pos2 r2]]
	(vector [pos1 r1] [pos2 r2] (- (+ r1 r2) (distance pos1 pos2))))

(defn differences [positions pos]
	(map (partial diff pos) positions))

(defn is-different? [pos-differences]
	(->> (map last pos-differences)
		 (filter neg?)
		 count
		 (> 11)
		 ))


(defn day23d [input]
	(->> (map (partial differences input) input)
		 (remove is-different?)
		 (apply concat)
		 (sort-by last)
		 (drop-while #(neg? (last %)))
		 (take-while #(= 0 (last %)))
		 (map butlast)
		 (map (partial apply single-overlap))
		 (map count)
		; (remove #(< 1 (count (filter (partial neg?) (last %)))))
	))

(defn day23c [input]
	(->> (map #(overlaps % input) input)
		 (map vector input)
		 (filter #(<= 989 (last %)))
		 (map first)

	))

;how many overlaps per point
;sort descending
;---------------------------------------


(defn dimensions [input]
	(->> (map first input)
		 (#(vector (map first %)
		 		   (map second %)
		 		   (map last %)))
		 (map #(vector (apply min %) (apply max %)))))



; pairwise overlap
; concat
; group-by count
; sort by first desc












