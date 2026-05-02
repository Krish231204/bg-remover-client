#!/usr/bin/env python3
"""
Background removal processor using rembg.
Takes an input image and outputs an image with background removed.
"""

import sys
import os
from pathlib import Path
from PIL import Image
from rembg import remove, new_session

def process_image(input_path: str, output_path: str) -> bool:
    """
    Remove background from image using rembg.
    
    Args:
        input_path: Path to input image
        output_path: Path to save output image
        
    Returns:
        True if successful, False otherwise
    """
    try:
        # Check if input file exists
        if not os.path.exists(input_path):
            print(f"Error: Input file not found: {input_path}", file=sys.stderr)
            return False
        
        # Open input image
        with open(input_path, 'rb') as i:
            input_data = i.read()
        
        # Use a lighter model by default to avoid OOM on small containers.
        model_name = os.getenv("REMBG_MODEL", "u2netp")
        session = new_session(model_name)
        output_data = remove(input_data, session=session)
        
        # Ensure output directory exists
        output_dir = os.path.dirname(output_path)
        if output_dir:
            os.makedirs(output_dir, exist_ok=True)
        
        # Save output image
        with open(output_path, 'wb') as o:
            o.write(output_data)
        
        print(f"Success: {output_path}")
        return True
        
    except Exception as e:
        print(f"Error: {str(e)}", file=sys.stderr)
        return False

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python3 rembg_processor.py <input_image> <output_image>", file=sys.stderr)
        sys.exit(1)
    
    input_file = sys.argv[1]
    output_file = sys.argv[2]
    
    success = process_image(input_file, output_file)
    sys.exit(0 if success else 1)
