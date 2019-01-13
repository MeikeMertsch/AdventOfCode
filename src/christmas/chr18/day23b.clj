(ns christmas.chr18.day23b)

(defn distance [pos bot]
	(->> (map - pos bot)
		 (map #(Math/abs %))
		 (apply +)))

(defn dimensions [bots]
	(->> (map (fn [[[x y z] r]] 
			(vector (- x r) (+ x r)
	 	 			(- y r) (+ y r)
	 	 			(- z r) (+ z r)))
			bots)
		 (reduce (fn [[x1 x2 y1 y2 z1 z2] [o1 o2 p1 p2 q1 q2]]
          	[(min x1 o1) (max x2 o2)
           	 (min y1 p1) (max y2 p2)
           	 (min z1 q1) (max z2 q2)]) [0 0 0 0 0 0])
		 (partition 2)))

(defn overlaps [[pos1 r1][pos2 r2]]
	(<= (distance pos1 pos2) (+ r1 r2)))

(defn all-overlaps [bot positions]
	(map (partial overlaps bot) positions))
	
(defn count-all-overlaps [[pos r] input]
	(->> (all-overlaps [pos r] input)
		 (filter true?)
		 count))

(defn max-side-length [dimensions]
	(->> (map reverse dimensions)
		 (map (partial apply -))
		 (apply max)
		 (#(if (odd? %) (inc %) %))))

(defn enclosing-octaeder-radius [max-side-len]
	(->> (Math/sqrt 2)
		 (* max-side-len)
		 Math/ceil
		 int))
;))

(defn middle [top-left-front msl]
	(map + top-left-front (repeat (/ msl 2))))

(defn init-pos [input]
	(let [quarder (dimensions input)
		  msl (max-side-length quarder)]
		(->> (vector (middle (map first quarder) msl)
					 (enclosing-octaeder-radius msl)))))

(defn new-middles [v spot]
	(let [w (Math/floor v)]
		(for [x [w (- w)]
			  y [w (- w)]
			  z [w (- w)]]
			(map + [x y z] spot))))

(defn break-into-subcubes [[spot r]]
	(->> (new-middles (/ r (Math/sqrt 32)) spot)
		 (map #(vector % (Math/ceil (/ r 2))))))

(defn candidates [octagons]
	(let [maximum (apply max (map last octagons))]
		(->> (filter #(= (last %) maximum) octagons)
			 (map first))))

(defn find-candidates [cubes input]
	(time (->> (mapcat break-into-subcubes cubes)
		 (map #(vector % (count-all-overlaps % input)))
		 candidates
		 distinct
		)))

(defn day23b [input]
	(->> (init-pos input)
		 vector
		 (iterate #(find-candidates % input))
		 (take-while #(<= 2 (last (last %))))
		 last
		 ;(#(apply max (map last %)))
		 ;(filter #(= (last %) ))
	)	)






