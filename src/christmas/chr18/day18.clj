(ns christmas.chr18.day18)

(defn handle-row [row row-nr]
	(->> (map-indexed #(vector [%1 row-nr] (str %2)) row)
		 (remove #(#{"."} (last %)))
		 (apply concat)))

(defn init [input]
	(->> (mapcat #(handle-row (nth input %) %) (range (count input)))
		 (apply hash-map)))

(defn get-type [type objects]
	(->> (filter #(#{type} (last %)) objects)
		 (apply concat)
		 (apply hash-map)))

(defn neighbours [[x y]]
	(vector
		[(dec x)(dec y)]
		[x (dec y)]
		[(inc x)(dec y)]
		[(dec x) y]
		[(inc x) y]
		[(dec x)(inc y)]
		[x (inc y)]	 
		[(inc x)(inc y)]))

(defn m3-objects [pos object objects]
	(->> (neighbours pos)
		 (map objects)
		 (filter #{object})
		 count
		 (< 2)))

(defn fill-open [pos objects]
	(if (m3-objects pos "|" objects) [pos "|"]))

(defn handle-tree [pos objects]
	(if (m3-objects pos "#" objects) [pos "#"] [pos "|"]))

(defn handle-lumberyard [pos objects]
	(->> (neighbours pos)
		 (map objects)
		 (#(and (some #{"#"} %)
		 		(some #{"|"} %)))
		 (#(if % [pos "#"]))))

(defn tick-spot [pos objects]
	(let [spot (get objects pos)]
		(cond 
			(nil? spot) (fill-open pos objects)
			(#{"|"} spot) (handle-tree pos objects)
			(#{"#"} spot) (handle-lumberyard pos objects))))

(defn tick-board [size objects]
	(->> (mapcat (fn [row] (mapcat #(tick-spot [% row] objects) (range size))) (range size))
		 (apply hash-map)))

(defn change-landscape [file its]
	(let [area (init file)
		  size (count file)]
		(->> (iterate #(tick-board size %) area)
			 (drop its)
			 first)))

(defn day18a [its file]
	(let [area (change-landscape file its)
		  trees (count (get-type "|" area))
		  lumberyards (count (get-type "#" area))]
		(vector trees
				lumberyards
				(* trees lumberyards))))

(defn get-vector [area]
	(let [trees (count (get-type "|" area))
		  lumberyards (count (get-type "#" area))]
		(vector trees
				lumberyards
				(* trees lumberyards))))


(defn day18b [file]
	(let [area (init file)
		  size (count file)]
		(->> (iterate #(tick-board size %) area)
			 (map get-vector)
			 (drop 1000)
			 first)))







