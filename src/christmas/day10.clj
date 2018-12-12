(ns christmas.day10)

(defn board [file]
	(let [columns (map first file)
		  rows (map last file)]
	[[(apply min columns) (apply min rows)]
	 [(apply max columns) (apply max rows)]]))

(defn size [stars]
	(let [[[col-min row-min][col-max row-max]] (board stars)]
	(* (- col-max col-min)
	   (- row-max row-min))))

(defn move-star [it [[x y][m n]]]
    	[(+ x (* m it))
    	 (+ y (* n it))])

(defn move-stars [it stars]
	(map #(move-star it %) stars))

(defn sizing [stars next-stars]
	{:smallest? (< (size stars) (size next-stars))
	 :smallest stars})

(defn find-candidate [stars]
	(->> (map #(move-stars % stars) (range))
		 (reductions sizing)
		 (drop-while #(false? (:smallest? %)))
		 first
		 ))

(defn star-pixels [stars]
	(apply assoc {} (interleave stars (repeat "X"))))

(defn paint-pixel [stars row column]
	(get stars [column row] "."))

(defn paint-row [c1 c2 stars row]
    (->> (map #(paint-pixel stars row %) (range c1 (inc c2)))
    	 (#(spit "resources/Output10" (str (apply str %) "\n") :append true))))
	
(defn paint-board [stars]
	(let [[[c1 r1][c2 r2]] (board stars)]
	(map #(paint-row c1 c2 (star-pixels stars) %) (range r1 (inc r2)))))

(defn day10 [input]
	(->> (find-candidate input)
		 paint-board))
