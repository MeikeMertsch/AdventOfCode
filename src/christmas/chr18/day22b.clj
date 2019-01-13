(ns christmas.chr18.day22b)

(defn erosion-level [depth geo-idx]
	(mod (+ geo-idx depth) 20183))
	
(defn pixel [depth geo-idx]
	(->> (erosion-level depth geo-idx)
		 (#(vector % (mod % 3)))))

(defn row-0 [depth columns]
	(->> (range)
		 (take (inc columns))
		 (map (partial * 16807))
		 (map (partial pixel depth))))

(defn px-0 [depth row-nr]
	(pixel depth (* row-nr 48271)))	

(defn next-px [depth prev top]
	(->> (map first [prev top])
		 (apply *)
		 (pixel depth)))

(defn next-row [[x y :as target] depth previous row-nr]
	(let [multiplier (if (= y row-nr) (concat (take x (repeat [1 1])) [(pixel depth 0)] (repeat [1 1])) (repeat [1 1]))
		  prev (map (partial map *) previous multiplier)]
	(reductions (partial next-px depth) 
				(px-0 depth row-nr) 
				(rest prev))))
	
(defn all-pixels [depth rows columns target]
	(reductions (partial next-row target depth)
				(row-0 depth columns)
				(range 1 (inc rows))))
	
(defn day22a [depth rows columns]
	(->> (all-pixels depth rows columns [columns rows])
		 (apply concat)
		 butlast
		 (map last)
		 (apply +)
		 (+ (mod (erosion-level depth 0) 3))))

(defn terrain [depth rows columns target]
	(->> (all-pixels depth rows columns target)
		 (mapv #(mapv last %))))

(defn transform-to-map [depth rows columns target]
	(->> (terrain depth rows columns target)
		 (map-indexed (fn [row-nr row] (map-indexed (fn [column-nr value] (vector [column-nr row-nr] value)) row)))
		 (apply concat)
		 (apply concat)
		 (apply hash-map)))

(defn valid-neighbours [{:keys [perms candidates maxima cave]} [[x y tool] dist]]
	(let [new-dist (inc dist)
		 [max-x max-y] maxima]
		(->> (hash-map [(dec x) y tool] new-dist
					   [x (dec y) tool] new-dist
				 	   [(inc x) y tool] new-dist
					   [x (inc y) tool] new-dist
					   [x y (mod (inc tool) 3)] (+ 7 dist)
					   [x y (mod (dec tool) 3)] (+ 7 dist))
			 (filter (fn [[[x1 y1 t1 :as spot1] d1]] (and (<= 0 x1 max-x)
			 			   			(<= 0 y1 max-y)
			 			   			(not= t1 (cave [x1 y1]))
			 			   			(if-let [perm-time (perms spot1)] (< d1 perm-time) true)
			 			   			(if-let [cand-time (candidates spot1)] (< d1 cand-time) true))))
			 (apply concat)
			 (apply hash-map))))

(defn tick [{:keys [perms candidates cave] :as status}]
	(let [candidate (first (sort-by (juxt last #(mod (dec (last (first %))) 3)) candidates))
		  neighbours (valid-neighbours status candidate)]
		(-> (update-in status [:perms] (partial apply assoc) candidate)
			(update-in [:candidates] dissoc (first candidate))
			(update-in [:candidates] (partial merge-with min) neighbours))))

(defn day22b [status target]
	(let [t (conj target 1)]
		(->> (iterate tick status)
			 (drop-while #(nil? ((:perms %) t)))
			 first
			 :perms
			 (#(% t)))))

