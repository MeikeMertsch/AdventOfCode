(ns christmas.day11b)

(defn parse-int [ch]
	(Integer/parseInt (str ch)))

(defn digit [number]
	(->> (str number)
		 (#(get % (- (count %) 3) 0))
		 parse-int))

(defn digit-2 [number]
	(- (quot number 100)
	   (* 10 (quot number 1000))))

(defn power [[x y] grid]
	(let [rack-id (+ 10 x)]
	(-> (* y rack-id)
		(+ grid)
		(* rack-id)
		digit-2
		(- 5))))

(defn iinc [number]
	(+ 2 number))


(defn allboardcoords [grid-start grid-size]
	(let [xcoords (range grid-start (inc grid-size))
		  ycoords (range grid-start (inc grid-size))]
         (mapcat (fn [ycoord] (map #(vector % ycoord) xcoords)) ycoords)))

(defn sum-coords [[x1 y1][x2 y2]]
	[(+ x1 x2)
	 (+ y1 y2)])

(defn power-of-a-cell [x y grid]
	(let [rack-id (+ 10 x)]
	(-> (* y rack-id)
		(+ grid)
		(* rack-id)
		digit-2
		(- 5))))

(defn power-of-a-row [x y grid grid-size]
	(->> (map #(power-of-a-cell % y grid) (range x (+ x grid-size)))
		 (apply +)))

(defn total-power-of-a-cell [[x y] grid grid-size] 
	(->> (map #(power-of-a-row x % grid grid-size) (range y (+ y grid-size)))
		 (apply +)))

(defn figure-out-max [some-map size]
	(->> (apply max (keys some-map))
		 (#(vector % size (get some-map %)))))


(defn all-total-powers [grid grid-size]
	(let [b (allboardcoords 0 (dec grid-size))]
	(-> (map #(total-power-of-a-cell % grid grid-size) b)
		(zipmap b)
		(figure-out-max grid-size))))





(defn total-power [coords grid size]
	(->> (map #(sum-coords coords %) (allboardcoords 0 (dec size)))
		 (map #(power % grid))
		 (apply +)))



(defn powers [coords size grid]
	(->> (map #(total-power % grid size) coords)
		 (#(interleave % coords))
	     (apply assoc {})))

;---------

(defn powered-board [grid]
	(let [all-coords (allboardcoords 1 300)]
	(->> (map #(power % grid) all-coords)
		 (#(interleave all-coords %))
		 (apply assoc {}))))

(defn cell-total-power [coords size p-board]
    (->> (allboardcoords 0 (dec size))
    	 (map #(sum-coords coords %))
    	 (map #(get p-board %))
    	 (apply +)))

(defn all-powers [coords size p-board]
	(->> (map #(cell-total-power % size p-board) coords)
		 (#(interleave % coords))
	     (apply assoc {})))

;--------

(defn largest-cell [grid grid-size]
	(->> (allboardcoords 1 (- 301 grid-size))
		 (#(powers % grid-size grid))
		 (#(let [the-max (apply max (keys %))]
		 	(vector grid-size
		 			(get % the-max))))))



(defn largest-cell-2 [grid grid-size p-board]
	(->> (allboardcoords 1 (- 301 grid-size))
		 (#(all-powers % grid-size p-board))
		 (#(let [the-max (apply max (keys %))]
		 	(vector (get % the-max) grid-size the-max)))))

(defn top [grid]
	(map #(largest-cell grid %) (range 2 10)))



(defn top-2 [grid start finish]
	(->> (map #(largest-cell-2 grid % (powered-board grid)) (range start finish))
		 (sort-by last >)
		 first
			))

(defn top-3 [grid]
	(->> (map #(all-total-powers grid %) (range 51 75))
		 (sort-by first >)
		 first
			))
	
	
