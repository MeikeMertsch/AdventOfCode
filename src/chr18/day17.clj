(ns christmas.day17
	(:require [clojure.string :as str]))

(defn handle-x [row]
	(->> (re-seq #"\d+" row)
		 (map #(Integer/parseInt %))
		 (#(map vector (repeat (first %)) (range (second %) (inc (last %)))))))

(defn handle-y [row]
	(->> (re-seq #"\d+" row)
		 (map #(Integer/parseInt %))
		 (#(map vector (range (second %) (inc (last %))) (repeat (first %))))))

(defn handle-row [row]
	(if (str/starts-with? row "x")
		(handle-x row)
		(handle-y row)))

(defn get-clay [file]
	(->> (slurp file)
		 str/split-lines
		 (mapcat handle-row)
		 (#(zipmap % (repeat "#")))))

(defn get-board 
	([file] 
		(get-board file #{[500 0]}))
	([file active] 
		(let [board (get-clay file)
		  coords (keys board)
		  xcoords (map first coords)
		  x1 (apply min xcoords)
		  x2 (inc (inc (apply max xcoords)))
		  y2 (inc (apply max (map last coords)))]
		  {:ground board
		   :x1 x1
		   :x2 x2
		   :y2 y2
		   :active active})))

(defn act-on-board [{:keys [ground x1 x2 y2]} func]
	(map (fn [row] (func (map #(get ground [% row] " ") (range x1 (inc x2))))) (range 0 (inc y2))))


(defn fall-target [[x y] board]
	(->> (:ground board)
		 keys
		 (filter #(= x (first %)))
		 (map last)
		 sort
		 (drop-while #(<= % y))
		 first))

(defn fall [[x y :as pos] board]
	(let [cand (fall-target pos board)
		  y2 (some identity [cand (:y2 board)])
		  deactivated (update-in board [:active] disj pos)]
;(println pos cand y2 deactivated)
		(->> (map vector (repeat x) (range (inc y) y2))
			 (#(interleave % (repeat "|")))
			 (update-in deactivated [:ground] #(apply assoc %1 %2))
			 (#(if (nil? cand) % (update-in % [:active] conj [x (dec cand)])))
		)))

(defn left-target [[x y :as pos] board]
	(->> (:ground board)
		 (filter #(= "#" (last %)))
		 (map first)
		 (filter #(= y (last %)))
		 (map first)
		 sort
		 (take-while #(< % x))
		 last))

(defn right-target [[x y :as pos] board]
	(->> (:ground board)
		 (filter #(= "#" (last %)))
		 (map first)
		 (filter #(= y (last %)))
		 (map first)
		 sort 
		 (drop-while #(<= % x))
		 first))

(defn has-bottomold [[x y] board]
	(let [coords (into #{} (keys (:ground board)))]
		(contains? coords [x (inc y)])))

(defn has-bottom [[x y] board]
	(let [ground (:ground board)]
		(#{"#" "~"}(get ground [x (inc y)]))))

(defn left-flow [[x y] x1 board]
	(->> (range x x1 -1)
		 (drop-while #(has-bottom [% y] board))
		 first))

(defn right-flow [[x y] x2 board]
	(->> (range x x2)
		 (drop-while #(has-bottom [% y] board))
		 first))

(defn flood [[x y] x1 x2 board]
	(let [activated (update-in board [:active] conj [x (dec y)])]
		(->> (map vector (range (inc x1) x2) (repeat y))
			 (#(interleave % (repeat "~")))
			 (update-in activated [:ground] #(apply assoc %1 %2)))))

(defn flood-wrong [[x y] x1 x2 board]
	(let [activated board]
		(->> (map vector (range (inc x1) x2) (repeat y))
			 (#(interleave % (repeat "~")))
			 (update-in activated [:ground] #(apply assoc %1 %2)))))

(defn overflow [x1 x2 y flows board]
	(let [active-ends (map #(vector % y) (remove nil? flows))
		  activated (update-in board [:active] #(apply conj %1 %2) active-ends)]
		(->> (map vector (range (inc x1) x2) (repeat y))
			 (concat active-ends)
			 (#(interleave % (repeat "|")))
			 (update-in activated [:ground] #(apply assoc %1 %2)))))

(defn spread [[x y :as pos] board]
	(let [l-cand (left-target pos board)
		  x1 (some identity [l-cand (:x1 board)])
		  r-cand (right-target pos board)
		  x2 (some identity [r-cand (:x2 board)])
		  deactivated (update-in board [:active] disj pos)
		  l-flow (left-flow pos x1 board)
		  r-flow (right-flow pos x2 board)]
;(println l-cand x1 r-cand x2 l-flow r-flow)
		(if 
			(every? nil? [l-flow r-flow]) 
			(flood pos x1 x2 deactivated)
			(overflow 	(some identity [l-flow x1])
						(some identity [r-flow x2])
						y
						[l-flow r-flow]
						deactivated))))

(defn spread-wrong [[x y :as pos] board]
	(let [l-cand (left-target pos board)
		  x1 (some identity [l-cand (:x1 board)])
		  r-cand (right-target pos board)
		  x2 (some identity [r-cand (:x2 board)])
		  deactivated (update-in board [:active] disj pos)
		  l-flow (left-flow pos x1 board)
		  r-flow (right-flow pos x2 board)]
;(println l-cand x1 r-cand x2 l-flow r-flow)
		(if 
			(every? nil? [l-flow r-flow]) 
			(spread [x (dec y)] (flood pos x1 x2 deactivated))
			(overflow 	(some identity [l-flow x1])
						(some identity [r-flow x2])
						y
						[l-flow r-flow]
						deactivated))))

(defn hit-water [pos board]
	(update-in board [:active] disj pos))

(defn tick-active [board]
(time
	(let [[x y :as pos] (first (:active board))
		  beneath (get (:ground board) [x (inc y)])]
(println pos (:active board) beneath)
		(cond 
			(nil? beneath) (fall pos board)
			(#{"#" "~"} beneath) (spread pos board)
			:rest (hit-water pos board)))))


(defn flood-field [file]
	(->> (get-board file)
		 (iterate tick-active)
;		 (drop 300)	
		 (drop-while #(not-empty (:active %)))
		 first
		)
	)

(defn day17a [file]
	(->> (flood-field file)
		 :ground
		 (filter #(#{"~"} (last %)))
		 count
		 ))












